package com.example.duiban.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.duiban.ProfileActivity
import com.example.duiban.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_camera_gallery_fragment.*

class Camera_Gallery_bottom_sheet_fragment: BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_camera_gallery_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_camera_bottom_sheet.setOnClickListener {
            Log.d("!!!", "Camera clicked")
            (activity as ProfileActivity?)!!.cameraFunction()
            dismiss()

        }

        imageView_gallery_bottom_sheet.setOnClickListener {
            Log.d("!!!", "Gallery clicked")
            (activity as ProfileActivity?)!!.galleryFunction()
            dismiss()
        }
    }
}