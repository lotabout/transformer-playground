a playground to write an annotation processor `@Transformer` for creating
transformers for classes.

## Scenario
In order to achieve encapsulation, sometimes we need to duplicate the code a
little bit. For example, you have a Business Object named `ApplicantBo`:

```java
public class ApplicantBo {
    private int id;
    private String name;
    private List<EducationVo> educationList;

    // don't want to go public
    private ZonedDateTime lastUpdate;

    // business logic here
}

public class EducationVo {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String school;
    private List<String> courses;
}
```

Somehow we need to send the data to other person/services. For example if we
want to send tha applicant information to browser, we might want to serialize
it to JSON. And for other cases, we might need to create a data class that
duplicate most of the fields:

```java
public class ApplicantPojo {
    private int id;
    private String name;
    private List<EducationPojo> educationList;
}

public class EducationPojo {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String school;
    private List<String> courses;
}
```

And things become tedious that we need to convert these two objects. So that
we need to write boilerplates for transforming:

```java
public class ApplicantBoTransformer {
    public static ApplicantPojo toPojo(ApplicantBo bo) {
        // ...
    }
}

public class EducationVoTransformer {
    public static EducationPojo toPojo(EducationVo vo) {
        // ...
    }
}
```

This is where `@Transformer` helps, it will generate the boilerplates
automatically.

## What @Transformer does
With the `ApplicantBo`, `ApplicantPojo` defined in last section, we can add
annotation to it:

```java
@Transformer(to={ApplicantPojo.class})
public class ApplicantBo {
    // ...
}

@Transformer(to = EducationPojo.class)
public class EducationVo {
    // ...
}
```

And `@Transformer` will grab the fields in two classes with the same name, and
generate the transformer automatically:

```java
public final class ApplicantBoTransformer {
  public static ApplicantPojo toApplicantPojo(ApplicantBo from) {
    ApplicantPojo to = new ApplicantPojo();;
    to.setId(from.getId());
    to.setName(from.getName());
    to.setEducationList(from.getEducationList().stream().map(l1 -> EducationVoTransformer.toEducationPojo(l1)).collect(Collectors.toList()));
    // Fields only in ApplicantBo
    // ZonedDateTime: lastUpdate
    // Fields only in ApplicantPojo
    return to;
  }
}

public final class EducationVoTransformer {
  public static EducationPojo toEducationPojo(EducationVo from) {
    EducationPojo to = new EducationPojo();;
    to.setStartTime(from.getStartTime());
    to.setEndTime(from.getEndTime());
    to.setSchool(from.getSchool());
    to.setCourses(from.getCourses().stream().map(l1 -> l1).collect(Collectors.toList()));
    // Fields only in EducationVo
    // Fields only in EducationPojo
    return to;
  }
}
```

Note that the `EducationVoTransformer` is detected automatically.

## Details
- syntax: `@Transformer(to = {a.class, b.class}, from = {c.class, d.class})`
- `@Transformer` will first find the fields with the same name from two
    classes (the one annotated and the one specified as "to" or "from").
- Then it will check if the types of the fields can be transformed or not
    - Two types can be transformed if they have the same type (primitive,
        declared class but not List or Map).
    - Two types can be transformed if `@Transformer` is used to connect the
        two types.
    - `List` can be transformed only if their bounded types are transformable.
    - `Map` can be transformed only if the type of the key is directly
        transformable and the value can be transformed by transformer.
- Array and other generic types are not supported.
