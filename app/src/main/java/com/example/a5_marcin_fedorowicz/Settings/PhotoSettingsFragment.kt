package com.example.a5_marcin_fedorowicz.Settings

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.media.Image
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.databinding.FragmentPhotoSettingsBinding
import com.example.a5_marcin_fedorowicz.databinding.ItemPhotoBinding
import com.example.a5_marcin_fedorowicz.imgDB.ImageDBItem
import com.example.a5_marcin_fedorowicz.imgDB.ImageRepository
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.util.UUID

class PhotoSettingsFragment : Fragment(){

    inner class MyGridAdapter(private var data: MutableList<Uri>, private val screenWidth: Int): RecyclerView.Adapter<MyGridAdapter.MyViewHolder>(){

        inner class MyViewHolder(viewBinding: ItemPhotoBinding): RecyclerView.ViewHolder(viewBinding.root){
            val img: ImageView = viewBinding.gridImageView
            val tv: TextView = viewBinding.text
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.img.setOnLongClickListener{
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
                builder.setTitle(resources.getString(R.string.yousure))
                builder.setMessage(resources.getString(R.string.sure_photo))
                builder.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    repo.delItem(position)
                    recreate(requireActivity())
                }
                builder.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                builder.create().show()
                true
            }
            holder.img.setOnClickListener{
                val bundle = Bundle().apply{
                    putInt("photoId", position)
                }
                findNavController().navigate(R.id.action_settingsFragment_to_photoSwipeFragment, bundle)
            }
            Glide.with(holder.itemView.context).load(data[position]).into(holder.img)
            holder.tv.text = data[position].toString()
            val layoutParams = holder.img.layoutParams
            layoutParams.width = screenWidth / 2 - (8 * 2)
            holder.img.layoutParams = layoutParams
        }
    }

    private lateinit var binding: FragmentPhotoSettingsBinding
    private var images = ArrayList<Uri>()
    private lateinit var repo: ImageRepository
    private lateinit var imgPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // _________________Camera______________________________________________________________________

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.camera -> {
                checkCameraPermissionAndOpenCamera()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkCameraPermissionAndOpenCamera(){
        if(ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePicture()
        }else{
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 6)
        }
    }

    private fun takePicture(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imgUri: Uri = FileProvider.getUriForFile(
            requireContext(),
            "com.example.a5_marcin_fedorowicz.fileProvider",
            saveFile()
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
        startActivityForResult(intent, 6)
    }

    private fun saveFile(): File{
        val uuid = UUID.randomUUID().toString()
        val path: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            uuid,
            ".jpg",
            path
        ).apply {
            imgPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 6){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePicture()
            }else{
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            var imgUri: Uri? = null
            when(requestCode){
                6 -> {
                    imgUri = Uri.fromFile(File(imgPath))
                }
                5 -> {
                    imgUri = data?.data
                }
            }
            saveUri(imgUri!!)
        }
    }

//__________________________________________________________________________________________________
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.camera_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoSettingsBinding.inflate(inflater, container, false)
        repo = ImageRepository.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.photoRecyclerView
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels

        loadData()

        val adapter = MyGridAdapter(images, screenWidth)
        recyclerView.adapter = adapter

        binding.addPhoto.setOnClickListener {
            importFromGallery()
        }
    }

    private fun loadData(){
        val imagesList = repo.getData()
        images = ArrayList()
        if (imagesList != null) {
            for(image in imagesList){
                val uri: Uri = Uri.parse(image.path)
                uri.let {images.add(it)}
            }
        }
    }

    // _________________Gallery_____________________________________________________________________

    private fun importFromGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 5)
    }

    private fun saveUri(imgUri: Uri){
        ImageRepository.getInstance(requireContext()).addItem(ImageDBItem(imgUri.toString()))
        recreate(requireActivity())
    }

    companion object {
        fun newInstance() =
            PhotoSettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

}