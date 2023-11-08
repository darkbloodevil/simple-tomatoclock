
trait SubjectObserver{
    type S <: Subject
    type O <: Observer
    trait Subject {
        self: S =>
            private var observers: List[O] = List()
            def subscribe(obs: O): Unit =
                observers = obs :: observers
            def publish() =
                for (i<-observers) i.notify(this)
        
    }
    trait Observer{
        def notify(sub: S): Unit
    }
}
object SensorReader extends SubjectObserver{
    type S = Sensor
    type O = Display
    
    class Sensor(val label: String) extends Subject{
        private var currentValue = 0.0
        def value = currentValue
        def changeValue(v: Double) =
            currentValue = v
        publish()
    }
    class Display extends Observer{
        def notify(sub: Sensor) =
            println(s"${sub.label} has value ${sub.value}")
    }
    
}
import SensorReader.*
@main def waya_main():Unit={
    println("waya")
    // setting up a network
    val s1 = Sensor("sensor1")
    val s2 = Sensor("sensor2")
    val d1 = Display()
    val d2 = Display()
    s1.subscribe(d1)
    s1.subscribe(d2)
    s2.subscribe(d1)
    
    // propagating updates through the network
    s1.changeValue(2)
    s2.changeValue(3)
    
    // prints:
    // sensor1 has value 2.0
    // sensor1 has value 2.0
    // sensor2 has value 3.0
}