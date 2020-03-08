package edu.uc.clemenks.keepingupwithschool.dto

data class Attendance (var name: String, var imageID: Int) {
    override fun toString(): String {
        return name
    }
}