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

If it is possible, always use construction injection. But in some cases we need to use method or field injection. (e.g Activity - we dont have access to constructor)

> *Important Question:*
> If all clients get their services from outside, who instantiates all these services?
> That is the most important question in context of dependency injection!!

Dependency Injection Architecturel Pattern (DIAP) is application wide dependency injection management system.
It's complex to achieve this larger scale structure. That's why we use frameworks to integrate dependency injection.
DIAP is not equal to fundamental techniques. It's much more.

## Law of Demeter

Class dependencies must be higher level. It means don't involve some unrelated classes to obtain dependencies.
It is really important to maintain code.

E.g : If our class needs to fetch data over retrofit, just get apiService object from constructor.
Don't pass retrofit object to class to initialize apiService inside class.

Characteristics:

- Principle of least knowledge

- Minimize class dependencies

- Makes code more readable and maintainable

## References

- https://www.udemy.com/course/dependency-injection-in-android-with-dagger