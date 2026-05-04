# License, Status and Contributing

------------------------------------------------------------------------------------------

**The Project is currently in development and will be licensed under GNU GPL** 

The GNU GPL (General Public License) is a free, copyleft software license that guarantees users the freedom to use, study, modify, and share software. It requires that any distributed modified versions are also released under the GPL, ensuring the same freedoms remain. The source code must be made available when distributing the software. GPL is designed to prevent proprietary use of open-source code. It protects user freedoms while encouraging collaboration and transparency.

## Contributing

**As a community project we expect the developers to adhere to the following standards:**

- ISO/IEC 12207: 
defines the full software lifecycle processes, including development, operation, maintenance, and supporting processes (like quality assurance, configuration management, and documentation).
It is widely used as a reference for establishing structured and standardized software engineering practices.

- Contributions should be announced as an issue before being developed 

- All contributions must comply with the defined code conventions of this project

- All contributions must be widly documented and successfully tested on multiple systems (localy) before being integrated and published

## Code Conventions 

**For Contributions, please follow listed Conventions**

### 1. Packages are clearly separated

```
BrainBoostBackend/
├── bean
├── config
├── controller 
├── domain 
├── repository 
├── service
├── worker 
└── resources
```
```
BrainBoostFrontend/
├── pulic 
│   ├──png
├── app/
│   ├── bean
│   ├── components
│   ├── guard
│   ├── service
└── └── store
```

## 2. Corerctly Naming and Language 

**The Project is strictly to be followed by Java Convetions. Those include:**

### Language 

**All content is written only in English**

### Variable and Function Names

**Vaiables and Functions are written in camelCase**

```
Long single; // One variable name is fully lowercase

String twoNames; //two variable names are lowercase+Uppercase

Int multipleNamesListed; //multiple variable are lowercase+Uppercase+Uppercase...

------------------------------------------------------------------------------------------

public String single(){}  // One function name is fully lowercase

public String twoNames(){}  //two function names are lowercase+Uppercase

public String single(){}  //multiple function are lowercase+Uppercase+Uppercase...
```

**Classes are written in PascalCase**

```
public class Single{} // Name with Uppercase start

public class TwoName{} // Both Name start with Uppercase

public class multipleNamesListed // All Names are started with Uppercase

```

**Entity-Classes end always on Entity**

```
public class TestEntity{}
```

**Table and Columns are snake_case**

```
@Column(name = "example")
private String example;
```

## 3. Lombok Usage

@Getters at the class level

@Setters only on mutable fields

**No setters for IDs**

```
@Getter
@Entity
public class FlashCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
}
```

## 4. Constructor

**No logic in the constructor.**

JPA entities require a public no-args constructor.

```
public FlashCardEntity() {}
```

## 5. Relationships

**Relationships are explicitly defined using @ManyToOne, @OneToMany, etc.**

Join columns are clearly named.

```
@ManyToOne
@JoinColumn(name = "creator_id")
@Setter
private UserEntity creator;
```

## 3. Beans / DTOs (bean)

Using records

DTOs are preferably implemented as records.

**Beans do not contain business logic.**

```
public record FlashCardBean(
        Long id,
        Long userId,
        String title,
        String question,
        String answer,
        Long lastLearned,
        Long flashCardSetId
) {}
```

**Mapping Methods**

Entity → Bean mapping is performed using a static `from` method.

No external mappers (e.g., MapStruct) are used without team approval.

```
public static FlashCardBean from(FlashCardEntity entity) {
    return new FlashCardBean(
            entity.getId(),
            entity.getCreator().getId(),
            entity.getTitle(),
            entity.getQuestion(),
            entity.getAnswer(),
            entity.getLastLearned(),
            entity.getFlashCardSet().getId()
    );
}
```
## 4. Bean Definitions

**Beans are explicitly defined using `@Bean`.**

Anonymous classes are allowed if they are clearly delimited.

```
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(@Nonnull CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200", "http://localhost")
                    .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    };
}
```

## 5. Spring Configuration (config)

**Configuration Classes**

Configuration classes end with `Config`

Always annotate with `@Configuration`

```
@Configuration
public class SecurityConfig {
```

## 6. Logging

System.out.println is only permitted temporarily.

For production code, a logger must be used. 

## 7. Default Rules

**Classes, methods, and variables are named meaningfully.**

**No magic numbers or uncommented hardcode.**

**Code is self-explanatory; comments are only used for complex logic.**

**Formatting follows the Java standard (IntelliJ Default).**

------------------------------------------------------------------------------------------