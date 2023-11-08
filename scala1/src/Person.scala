class Personv2:
    var name = ""
    var age = 0
    override def toString = s"$name is $age years old"

object Personv2:
    
    // a one-arg factory method
    def apply(name: String): Person =
        var p = new Person
        p.name = name
        p
    
    // a two-arg factory method
    def apply(name: String, age: Int): Person =
        var p = new Person
        p.name = name
        p.age = age
        p

end Personv2

@main def person_main():Unit={
    val joe = Personv2("Joe")
    val fred = Personv2("Fred", 29)
    print(joe)
    print(fred)
}
