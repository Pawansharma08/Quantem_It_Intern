package com.example.quantem_it_intern.Fragment

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.quantem_it_intern.Home_Activity
import com.example.quantem_it_intern.R
import com.example.quantem_it_intern.Utils.TokenManager
import com.example.quantem_it_intern.databinding.FragmentSignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupFrag : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    val firebase = FirebaseAuth.getInstance()
    val auth = Firebase.auth
    private lateinit var tokenManager : TokenManager
    private val email: EditText? = null

    private lateinit var googleSigninClient : GoogleSignInClient
    private lateinit var alert : androidx.appcompat.app.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        email?.findViewById<EditText>(R.id.et_Email)


    }

    private fun sigInGoogle(){
        val siginInIntent = googleSigninClient.signInIntent
        launcher.launch(siginInIntent)

    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account:GoogleSignInAccount?  = task.result
            if(account!= null){
//                updateUI(account)
            }
        }
        else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }

    }
//    private fun updateUI(account: GoogleSignInAccount?) {
//        val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
//        auth.signInWithCredential(credential).addOnCompleteListener {
//            if(it.isSuccessful) {
//                account?.email?.let { it1 -> tokenManager.saveEmail(it1) }
//                account?.givenName?.let { it-> tokenManager.saveGivenName(it) }
//                account?.photoUrl?.let { it->tokenManager.savePic(it.toString()) }
//                tokenManager.saveUserName(account?.email?.replace("@gmail.com","")?.trim()!!)
//                tokenManager.saveToken(account.idToken.toString())
//                Log.e("TAG",tokenManager.getPic().toString())
//                alert.dismiss()
//                val intent = Intent(requireContext(),Home_Activity::class.java)
//                requireActivity().startActivity(intent)
//
//
//
//
//            }else {
//                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
//                Log.e("TAG",it.exception.toString())
//                alert.dismiss()
//            }
//        }
//
//    }
//


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser

//        tokenManager = TokenManager(requireContext())
//        val gson = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSigninClient = GoogleSignIn.getClient(requireActivity(),gson)
        binding.RegisterBtn.setOnClickListener {

            val etName:String = binding.etName.toString().trim()
            val emails = binding.etEmail.text.toString()
            val etPhone = binding.etPhone.toString().trim()
            val etPass = binding.etPassword.toString().trim()
        Log.e("TAG",binding.etEmail.text.toString())
        Log.e("TAG",binding.etPassword.text.toString())

            auth.createUserWithEmailAndPassword(emails, etPass)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(requireActivity(),Home_Activity::class.java)
                        requireActivity().startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
//                        updateUI(null)
                    }
                }


                /*
                binding.RegisterBtn.setOnClickListener {
                    auth?.signInWithEmailAndPassword(etEmail, etPass)
                        ?.addOnCompleteListener() { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val user = auth?.currentUser
                                val intent = Intent(context,LoginFrag::class.java)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    context,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                        }*/
                    }
                }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return emailRegex.matches(email)
    }


}