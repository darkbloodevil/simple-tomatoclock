trait HasLegs:
    def numLegs: Int
    def walk(): Unit
    def stop() = println("Stopped walking")

trait HasTail:
    def tailColor: String
    def wagTail() = println("Tail is wagging")
    def stopTail() = println("Tail is stopped")
    
class IrishSetter(name: String) extends HasLegs, HasTail:
    val numLegs = 4
    val tailColor = "Red"
    def walk() = println("I’m walking")
    override def toString = s"$name is a Dog"
    
@main def aya():Unit={
    print(IrishSetter("Aya"))
}