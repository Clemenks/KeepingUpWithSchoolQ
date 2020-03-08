package edu.uc.clemenks.keepingupwithschool.service

import androidx.lifecycle.MutableLiveData
import edu.uc.clemenks.keepingupwithschool.dto.Attendance

class AttendanceService {
    fun getAttendance(name: String) : MutableLiveData<ArrayList<Attendance>> {
        return MutableLiveData<ArrayList<Attendance>>()
    }
}