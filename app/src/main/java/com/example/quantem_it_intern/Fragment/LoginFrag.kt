package com.example.quantem_it_intern.Fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.quantem_it_intern.Home_Activity
import com.example.quantem_it_intern.R
import com.example.quantem_it_intern.Utils.TokenManager
import com.example.quantem_it_intern.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFrag : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    val firebase = FirebaseAuth.getInstance()

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSigninClient: GoogleSignInClient
    private lateinit var alert: androidx.appcompat.app.AlertDialog
    private lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenManager = TokenManager(requireContext())
        auth = FirebaseAuth.getInstance()
        val gson = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSigninClient = GoogleSignIn.getClient(requireActivity(), gson)
        binding.btnLogin.setOnClickListener {
            val etEmail: String = binding.etEmail.text.toString()
            val etPass = binding.etPassword.text.toString().trim()
            binding.btnLogin.setOnClickListener {
                auth.signInWithEmailAndPassword(etEmail, etPass)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val intent = Intent(context, Home_Activity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
        binding.imgBtnGoogle.setOnClickListener {

            sigInGoogle()
        }
        /*    fun firebaseAuthWithGoogle(idToken: String, accessToken: String) {
                val credential = GoogleAuthProvider.getCredential(idToken, accessToken)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser

                        } else {
                            Toast.makeText(context, "Fail to Login", Toast.LENGTH_SHORT).show()
                        }
                    }
            }*/
    }

/*        binding.imgBtnFacebook.setOnClickListener {
            fun firebaseAuthWithFacebook(idToken: String) {
                val credential = FacebookAuthProvider.getCredential(idToken)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                        } else {
                            Toast.makeText(context, "Fail to Login", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }*/


    private fun sigInGoogle() {
        val siginInIntent = googleSigninClient.signInIntent
        launcher.launch(siginInIntent)

    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_CANCELED) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
                val intent = Intent(requireContext(), Home_Activity::class.java)
                requireActivity().startActivity(intent)
            }else{
                Log.e("TAG",result.toString())
                Log.e("TAG",result.resultCode .toString())
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {

        }

    }


    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                account.email?.let { it1 -> tokenManager.saveEmail(it1) }
                account.givenName?.let { it -> tokenManager.saveGivenName(it) }
                account.photoUrl?.let { it -> tokenManager.savePic(it.toString()) }
                tokenManager.saveUserName(account.email?.replace("@gmail.com", "")?.trim()!!)
                tokenManager.saveToken(account.idToken.toString())
                Log.e("TAG", tokenManager.getPic().toString())
                alert.dismiss()
                val intent = Intent(requireContext(), Home_Activity::class.java)
                requireActivity().startActivity(intent)


            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                Log.e("TAG", it.exception.toString())
                alert.dismiss()
            }
        }


    }
}

