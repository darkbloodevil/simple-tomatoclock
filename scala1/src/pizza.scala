// https://docs.scala-lang.org/scala3/book/domain-modeling-fp.html



enum CrustSize:
    case Small, Medium, Large

enum CrustType:
    case Thin, Thick, Regular

enum Topping:
    case Cheese, Pepperoni, BlackOlives, GreenOlives, Onions

// the companion object of enumeration Topping
object Topping:
    // the implementation of `toppingPrice` above
    def price(t: Topping): Double = 2


case class Pizza(
                    crustSize: CrustSize,
                    crustType: CrustType,
                    toppings: Seq[Topping]
                )

// the companion object of case class Pizza
object Pizza:
    // the implementation of `pizzaPrice` from above
    def price(p: Pizza): Double = 20

trait PizzaServiceInterface:
    
    def price(p: Pizza): Double
    
    def addTopping(p: Pizza, t: Topping): Pizza
    def removeAllToppings(p: Pizza): Pizza
    
    def updateCrustSize(p: Pizza, cs: CrustSize): Pizza
    def updateCrustType(p: Pizza, ct: CrustType): Pizza

case class Address(
                      street1: String,
                      street2: Option[String],
                      city: String,
                      state: String,
                      zipCode: String
                  )

case class Customer(
                       name: String,
                       phone: String,
                       address: Address
                   )

case class Order(
                    pizzas: Seq[Pizza],
                    customer: Customer
                )

import Topping.*
def toppingPrice(t: Topping): Double = t match
    case Cheese | Onions => 0.5
    case Pepperoni | BlackOlives | GreenOlives => 0.75

import CrustSize.*
import CrustType.*
def crustPrice(s: CrustSize, t: CrustType): Double =
    (s, t) match
        // if the crust size is small or medium,
        // the type is not important
        case (Small | Medium, _) => 0.25
        case (Large, Thin) => 0.50
        case (Large, Regular) => 0.75
        case (Large, Thick) => 1.00
    
def pizzaPrice(p: Pizza): Double = p match
    case Pizza(crustSize, crustType, toppings) =>
        val base  = 6.00
        val crust = crustPrice(crustSize, crustType)
        val tops  = toppings.map(toppingPrice).sum
        base + crust + tops


object PizzaService extends PizzaServiceInterface:
    
    def price(p: Pizza): Double =
        pizzaPrice(p) // implementation from above
    
    def addTopping(p: Pizza, t: Topping): Pizza =
        p.copy(toppings = p.toppings :+ t)
    
    def removeAllToppings(p: Pizza): Pizza =
        p.copy(toppings = Seq.empty)
    
    def updateCrustSize(p: Pizza, cs: CrustSize): Pizza =
        p.copy(crustSize = cs)
    
    def updateCrustType(p: Pizza, ct: CrustType): Pizza =
        p.copy(crustType = ct)

end PizzaService


import PizzaService.*
@main def pizza_main()={
    val pizza1 = Pizza(Small, Thin, Seq(Cheese, Onions))
    
    val p = Pizza(Small, Thin, Seq(Cheese))
    
    // how you want to use the methods in PizzaServiceInterface
    val p1 = addTopping(p, Pepperoni)
    val p2 = addTopping(p1, Onions)
    val p3 = updateCrustType(p2, Thick)
    val p4 = updateCrustSize(p3, Large)
    print(PizzaService.price(p4))
    
    
//    extension (p: Pizza)
//        def price: Double =
//            pizzaPrice(p) // implementation from above
//
//        def addTopping(t: Topping): Pizza =
//            p.copy(toppings = p.toppings :+ t)
//
//        def removeAllToppings: Pizza =
//            p.copy(toppings = Seq.empty)
//
//        def updateCrustSize(cs: CrustSize): Pizza =
//            p.copy(crustSize = cs)
//
//        def updateCrustType(ct: CrustType): Pizza =
//            p.copy(crustType = ct)
//
//    println(Pizza(Small, Thin, Seq(Cheese))
//        .addTopping(Pepperoni)
//        .updateCrustType(Thick)
//        .price)
}