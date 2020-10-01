fun <T> foo(receiver: T, op: (T) -> Unit): T {
    op(receiver)
    return receiver
}

fun <T> T.bar(op: (T) -> Unit): T {
    op(this)
    return this
}

fun <T> T.baz(op: T.() -> Unit): T { 
    this.op()    
    return this
}

fun main() {
    foo("ciao", { println(it) })
//  ciao
//  res8: kotlin.String = ciao
    foo("ciao") { println(it) } 
//  ciao
//  res9: kotlin.String = ciao
    "ciaone".bar({ println(it) })
//  ciaone
//  res14: kotlin.String = ciaone
    "ciaone".bar { println(it) }
//  ciaone
//  res15: kotlin.String = ciaone

//  "something".baz({ it.println() })   
//  error: unresolved reference: it

//  "something".baz({ this.println() })
//  error: unresolved reference: println

//  "something".baz({ this.println() })
//  error: unresolved reference: println

//  "something".baz({ String.println() })
//  error: unresolved reference: println

    "something".baz { println(this) }
//  something
//  res24: kotlin.String = something
}
