# Contextual Sessions

Contextual sessions are a feature of Spring ORM.  When an MVC method is called
Spring automatically creates a session and associates it with the handler thread.
We can get access to the session using Spring by autowiring the session in our own
code. Through this, we can call basic Hibernate behavior, use HQL, etc.

# @Transactional annotation
You can place the @Transactional annotation over a method, which declares that that
method should operate in a transactional context. You can imagine this as it creating
a transaction on the session prior to calling the method, and committing that transaction
afterwords.

However, the @Transactional annotation allows for a lot of specific configuration
in order to make reusable methods that can optionally be a part of, isolated from, or 
nest into other contextual transactions.

## Transaction Propagation Levels

1. Mandatory - There must be an outer transaction when a method with this level is
called. If there is not, an exception is thrown.

2. Nested -  If a transaction exists, pause it and run this as a nested transaction
or if none exists, create a transaction for this method.

3. Never - Should not operate transactionally. If a transaction is ongoing throws
an exception.

4. Not Supported - Method cannot execute transactionally. If a transaction is ongoing
pause it for the duration of the method call, and return to it afterwords.

5. Required - It will use an ongoing transaction if one exists, otherwise creates
a transaction.

6. Requires New - Always creates a new transaction for this method, regardless of
outer transactional context.

7. Supports - Will use a transaction if one is ongoing, 
	otherwise operates non-transactionally.

