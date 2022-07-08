package com.dnc.kt.quantumassignment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import com.dnc.kt.quantumassignment.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class signInFragment : Fragment() {
    private lateinit var binding:FragmentSignInBinding
    private val auth: FirebaseAuth = Firebase.auth
    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy { GoogleSignIn.getClient(requireContext(), gso) }

    private val launcher = registerForActivityResult(GoogleSignInContract()){
        it.onSuccess { acc ->
            firebaseAuthWithGoogle(acc.idToken ?: throw IllegalArgumentException("Id token cannot be null, but was null."))
        }
            .onFailure {
//                viewModel.state.value = LoginState.Failed("Cancelled")
            }
    }
    private fun signIn() {
        launcher.launch(googleSignInClient)
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                val  intent= Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .addOnFailureListener {
//                state.value = LoginState.Failed("Cannot sign in!")
            }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignInBinding.inflate(layoutInflater)
        binding.googleimg.setOnClickListener {
            signIn()
        }

        val email=binding.email.text.toString()
        val password=binding.password.text.toString()
        val auth= Firebase.auth

        binding.btnLogin.setOnClickListener {


            if(email.isNullOrEmpty()==true){
                binding.email.error="Invalid Email"
            }
            else if(password.isNullOrEmpty()==true){
                binding.password.error="Invalid Password"
            }
            else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("EmailPasswordSignIn", "signInWithEmail:success")
                            val  intent= Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
//                        findNavController().navigate(R.id.mainFragment_to_homeFragment)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("EmailPasswordSignIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(requireContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }
        return (binding.root)


    }




}

class GoogleSignInContract: ActivityResultContract<GoogleSignInClient, Result<GoogleSignInAccount>>() {
    override fun createIntent(context: Context, input: GoogleSignInClient?): Intent {
        return input!!.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Result<GoogleSignInAccount> {
        return GoogleSignIn.getSignedInAccountFromIntent(intent)
            .runCatching {
                getResult(ApiException::class.java)
            }
    }
}