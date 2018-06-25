package com.example.chendaole.freecn.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.chendaole.freecn.R
import com.example.chendaole.freecn.utils.PluginUtils
import dalvik.system.DexClassLoader


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ApplicationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ApplicationFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ApplicationFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_application, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEvent()
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
         * @return A new instance of fragment ApplicationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ApplicationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


    fun initEvent() {
        val btnCallPlugin = view.findViewById(R.id.btn_call_plugin) as Button
        btnCallPlugin.setOnClickListener{
            callPlugin()
        }
    }

    /**
     * 调用插件
     * **/
    fun callPlugin() {
       var plugin =PluginUtils.getPlugin(activity, "plugin-debug.apk")

       val dex: DexClassLoader? = plugin.instance()

      //  val intent: Intent = Intent("com.example.plugin", null)
      //  val pm: PackageManager = activity.packageManager
      //  val resolveinfoes = pm.queryIntentActivities(intent, 0)
       // val actInfo = resolveinfoes.get(0).activityInfo
       // val packName: String = actInfo.packageName



       val cls: Class<*>? = dex!!.loadClass("com.example.plugin.MainActivity")
        val obj: Any  = cls!!.newInstance()

        val intent = Intent()
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setClass(activity, obj::class.java)
        activity.startActivity(intent)
    }

}
