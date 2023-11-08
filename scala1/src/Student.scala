import java.time.*


// [1] the primary constructor
class Student(
                 var name: String,
                 var govtId: String
             ):
    private var _applicationDate: Option[LocalDate] = None
    private var _studentId: Int = 0
    
    // [2] a constructor for when the student has completed
    // their application
    def this(
                name: String,
                govtId: String,
                applicationDate: LocalDate
            ) =
        this(name, govtId)
        _applicationDate = Some(applicationDate)
    
    // [3] a constructor for when the student is approved
    // and now has a student id
    def this(
                name: String,
                govtId: String,
                studentId: Int
            ) =
        this(name, govtId)
        _studentId = studentId

@main def main(): Unit = {
    val student=Student("Mary", "123")
}

