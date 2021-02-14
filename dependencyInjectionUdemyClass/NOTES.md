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

## References

- https://www.udemy.com/course/dependency-injection-in-android-with-dagger