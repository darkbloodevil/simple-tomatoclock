@main def miyaha()={
    val double = (i: Int) => i * 2
    val triple = (i: Int) => i * 3
    val functionMap = Map(
        "2x" -> double,
        "3x" -> triple
    )
    println(functionMap("2x")(2))
}
