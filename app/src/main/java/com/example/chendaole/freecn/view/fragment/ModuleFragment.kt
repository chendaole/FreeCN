package com.example.chendaole.freecn.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.chendaole.freecn.R
import com.example.chendaole.freecn.SubFreeCNActivity
import com.example.chendaole.freecn.utils.AlertDialogUtils
import com.example.chendaole.freecn.utils.DexClassLoaderUtils
import com.example.chendaole.freecn.utils.FileUtils
import com.example.chendaole.freecn.utils.ToastUtils
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ModuleFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ModuleFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ModuleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btn = getView().findViewById<Button>(R.id.button_load_Jar)

        btn.setOnClickListener {
            onClickLoadJar()
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    fun onClickLoadJar() {
        val am: AssetManager = this.context.assets
        val fileList: Array<String>  = am.list("")
        var jarFileList: Array<String> = arrayOf()

        for (filename: String in fileList) {
            var which:Int = filename.lastIndexOf(".")
            if (which > -1 ) {
                var fileAttr = filename.substring(which + 1).toLowerCase()
                if (fileAttr == "jar") {
                    jarFileList +=  arrayOf(filename)
                }
            }
        }

        AlertDialogUtils.items(this.context, "assets", jarFileList, object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val filename = jarFileList[which]
                val targetPath = DexClassLoaderUtils.getDexDirsPath(this@ModuleFragment.context) + File.separator + filename

                if (!FileUtils.copyAssetFile(am, filename, targetPath)) {
                    ToastUtils.show(this@ModuleFragment.context, "文件迁移失败")
                    return
                }

                val intent = Intent()
                intent.putExtra("filename", jarFileList[which])
                intent.setClass(this@ModuleFragment.context, SubFreeCNActivity::class.java)
                this@ModuleFragment.startActivity(intent)
            }
        }).show()

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ModuleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ModuleFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


}
