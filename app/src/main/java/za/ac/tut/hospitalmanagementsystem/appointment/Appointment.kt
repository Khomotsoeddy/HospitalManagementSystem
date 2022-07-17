package za.ac.tut.hospitalmanagementsystem.appointment

data class Appointment (
    var appointmentId:String,
    var patientId:String,
    var doctorId:String,
    var specialization:String,
    var description:String,
    var time:String,
    var submitDate:String,
    var date: String,
    var status: String)