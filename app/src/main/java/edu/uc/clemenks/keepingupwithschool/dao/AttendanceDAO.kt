package edu.uc.clemenks.keepingupwithschool.dao

import edu.uc.clemenks.keepingupwithschool.dto.Attendance
import retrofit2.Call

interface AttendanceDAO {

    fun getAttendance(): Call<ArrayList<Attendance>>
}