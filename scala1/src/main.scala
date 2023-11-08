import scala.io.StdIn.readLine
import scala.math.*


class Complex(real: Double, imaginary: Double) {
    def re() = real
    
    def im() = imaginary
}

// getClassAsString is a method that takes a single argument of any type.
def getClassAsString(x: Matchable): String = x match
    case s: String => s"'$s' is a String"
    case i: Int => "Int"
    case d: Double => "Double"
    case l: List[?] => "List"
    case _ => "Unknown"

trait Speaker:
    def speak(): String // has no body, so it’s abstract

trait TailWagger:
    def startTail(): Unit = println("tail is wagging")
    
    def stopTail(): Unit = println("tail is stopped")

class Dog(name: String) extends Speaker, TailWagger :
    def speak(): String = "Woof!"

trait AddService:
    def add(a: Int, b: Int) = a + b

trait MultiplyService:
    def multiply(a: Int, b: Int) = a * b

// implement those traits as a concrete object
object MathService extends AddService, MultiplyService

class Person:
    var name = ""
    var age = 0
    override def toString = s"$name is $age years old"
    

object Person:
    
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

end Person

enum Planet(mass: Double, radius: Double):
    private final val G = 6.67300E-11
    def surfaceGravity = G * mass / (radius * radius)
    def surfaceWeight(otherMass: Double) =
        otherMass * surfaceGravity
    
    case Mercury extends Planet(3.303e+23, 2.4397e6)
    case Earth   extends Planet(5.976e+24, 6.37814e6)


object HelloWorld {
    def main(args: Array[String]): Unit = {
        println(Complex(10, 2).re().self)
        val x = List(1, 2, 3)
        println(x.filter(_ > 1))
        var name = "aaaa"
        val password = 123
        val b: String | Int = if (true) name else password
        println(b)
        println("Please enter your name:")
        //    name = readLine()
        println("Hello, " + name + "!")
        for
            i <- 1 to 3
            j <- 'a' to 'c'
            if i > 1
            if j != 'b'
        do
            println(s"i = $i, j = $j")
        
        // examples
        println(getClassAsString(1)) // Int
        getClassAsString("hello") // 'hello' is a String
        getClassAsString(List(1, 2, 3)) // List
        
        val dog = Dog("dog")
        
        extension (s: String)
            def makeInt(radix: Int): Int = Integer.parseInt(s, radix)
        
        println("1".makeInt(2))
        // use the object
        import MathService.*
        println(add(1, 1)) // 2
        println(multiply(2, 2)) // 4
        
        case class Point(x: Double, y: Double)
        extension (sc: StringContext)
            def p(args: Double*): Point = {
                // reuse the `s`-interpolator and then split on ','
                val pts = sc.s(args: _*).split(",", 2).map {
                    _.toDoubleOption.getOrElse(0.0)
                }
                Point(pts(0), pts(1))
            }
        println(p"1, -2" )
        
        val a_map=Map(
            "all"->10,
            "guaaa"->3,
            "waaaaa"->2
        )
        for (i,j)<-a_map do println(s"i=$i j=$j")
        
        for i <-(0 until 5) do
            i match
                case 0=>println("waaa")
                case _=>println("laaa")
                
        class stupid:
            override def toString: String = "stupid"
    
        println(stupid())
        
    
        class Circle(val radius: Double):
            def area: Double = Circle.calculateArea(radius)
    
        object Circle:
            private def calculateArea(radius: Double): Double = Pi * pow(radius, 2.0)
            
        println(Circle(0.1).area)
        
        println(Person)
        
        println(Planet.Earth)
        
    }
}

