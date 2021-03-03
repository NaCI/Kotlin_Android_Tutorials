# SUMMARY

**Dependency Injection :** Providing services to clients from "outside"

Fundamental Dependency Injection Techniques :

- Constructor Injection : Pass dependencies to class from constructor

    - Pros
        - Simple
        - We can see all dependencies when creating class (read friendly)
        - Injected fields can be finalized
        - Easy to mock services in unit tests

    - Cons
        - None

- Method Injection : Pass dependencies to class via method call

    - Pros
        - Can happen after construction

    - Cons
        - Can lead to implicit ordering requirements (temporal coupling)

- Field(Property) Injection : Set public field/property of class from outside (In Kotlin we use propery instead of methods)

    - Pros
        - Can happen after construction

    - Cons
        - Can lead to implicit ordering requirements (temporal coupling)

If it is possible, always use construction injection. But in some cases we need to use method or field injection.
(e.g Activity - we dont have access to constructor)

> *Important Question:*
> If all clients get their services from outside, who instantiates all these services?
> That is the most important question in context of dependency injection!!

Dependency Injection Architecturel Pattern (DIAP) is application wide dependency injection management system.
It's complex to achieve this larger scale structure. That's why we use frameworks to integrate dependency injection.
DIAP is not equal to fundamental techniques. It's much more.

According to DIAP our application should be seperated into two disjoint sets:

- Construction set (classes needs to be alive for functional sets work properly)

- Functional set

- Integration logic between these two sets

## Law of Demeter

Class dependencies must be higher level. It means don't involve some unrelated classes to obtain dependencies.
It is really important to maintain code.

E.g : If our class needs to fetch data over retrofit, just get apiService object from constructor.
Don't pass retrofit object to class to initialize apiService inside class.

Characteristics:

- Principle of least knowledge

- Minimize class dependencies

- Makes code more readable and maintainable

## Main Benefit of Dependency Injection

Non-repetitive definition and exposure of the entire object graph by composition root is the main benefit of dependency injection.

With dependency injection, we can keep our classes small and focused, but still easily compose them into arbitrary long chains
to achieve complex functionality.

Other main benefits of Dependency Injection is that it enables **Single Responsibility Principle** and **Reusability**

With Dependency Injection, developer being able to separate the functionality into small, narrowly focused
classes that adhere to the single responsibility principle and then being able to easily reduce this classes
when you construct complex, logical flows. We basically compose this classes into larger flows.

## Objects vs Data Structures

Object: contains behaviour

Data Structure: contains only data

Dependency Injection Architectural Pattern deals with objects, not data structures.
Therefore, you should not expose data structures on your object graph (composition roots, components in dagger) either internally or externally.

E.g:

```Java
public class DataAggregator() {
    private final List<Data> mData;

    public DataAggregator() {
        mData = new LinkedList<>();   // -----> this doesn't violate dependency injection principle, as its data structure not object.
    }
}
```

## Dagger2 Scopes

- Component that provide scoped services must be scoped

- All clients get the same instance of a scoped service **from same instance** of a Component

## Dagger2 Injector

- Void methods with single argument defined on components, generate injectors for the type of the argument

E.g:

```kotlin
@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(questionsListFragment: QuestionsListFragment)
}
```

- Client's non-private non-final properties(fields) annotated with @Inject designate injection targets

E.g:

```kotlin
class QuestionsListFragment : BaseFragment() {

    @Inject
    lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase
}
```

## Dagger2 Multi-Module Components

- Components can use multiple modules

- Modules of a Single Component share the same object graph

E.g:

```kotlin
@PresentationScope
@Subcomponent(modules = [PresentationModule::class, UseCaseModule::class])
interface PresentationComponent {
    ...
}

// We can use any object which is provided by PresentationModule in the UseCaseModule
```

- Dagger automatically instantiate modules with no-argument constructors

## Dagger2 Automatic Discovery of Services

- Dagger can automatically discover services having a public constructor annotated with *@Inject* annotation

- Automatically discovered services can be scoped

- It's best to keep scoped services inside modules in one single place

## Dagger2 Component.Builder

- Dagger generates more performant code for static providers in Modules (use companion object or top level object in Kotlin)

- @Component.Builder (or @Subcomponent.Builder) designates inner builder interface for Component

- @BindsInstance allows for injection of "bootstrapping dependencies" (constructor dependencies for module)
directly into Component builders

## References

- https://www.udemy.com/course/dependency-injection-in-android-with-dagger