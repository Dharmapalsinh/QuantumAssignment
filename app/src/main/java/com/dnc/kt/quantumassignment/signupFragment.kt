package com.dnc.kt.quantumassignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.dnc.kt.quantumassignment.databinding.FragmentSignupBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.flow.MutableStateFlow


class signupFragment : Fragment() {

    private val viewModel : RegisterViewModel by viewModels()
    private lateinit var binding: FragmentSignupBinding
    val auth= Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignupBinding.inflate(layoutInflater)

        binding.btnRegister.setOnClickListener {
            val Name=binding.Name.text
            val Email=binding.email.text
            val Password=binding.password.text
            val contact=binding.Contact.text
            val checkbox=binding.checkbox.isChecked
            if(Name.isNullOrBlank()  || Email.isNullOrBlank() || Password.isNullOrBlank() || contact.isNullOrBlank()){
                Toast.makeText(context,"All Fields Are Mandatory", Toast.LENGTH_SHORT).show()
            }
            else{

                when(true){
                    binding.password.length() < 8 -> Toast.makeText(context,"Password Should be greater than 8 characters", Toast.LENGTH_SHORT).show()
                    binding.Contact.length() <10 -> Toast.makeText(context,"Contact No should be of 10 digits",
                        Toast.LENGTH_SHORT).show()
                    else -> {
                        if(checkbox == true){
                            val name=binding.Name.text.toString()
                            val email=binding.email.text.toString()
                            val password=binding.password.text.toString()
                            var phone:Int=0
//                            try{
//                                var phone:Int=binding.Contact.text.toString().toInt()
//                            }
//                            catch (e:NumberFormatException){
//                                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
//                            }
                            auth.createUserWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString())
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("EmailPassword", "createUserWithEmail:success")
//                                        findNavController().navigate(R.id.mainFragment_to_homeFragment)
                                        viewModel.adduser(name,password,phone,email)
                                        Toast.makeText(context,"register successful", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//                                        requireActivity().onBackPressed()
                                        val  intent= Intent(requireContext(), MainActivity::class.java)
                                        startActivity(intent)
                                        requireActivity().finish()

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("EmailPassword", "createUserWithEmail:failure", task.exception)
                                        Toast.makeText(requireContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }


                        }
                        else{
                            Toast.makeText(context,"Accept T&C", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }
//            viewModel.adduser(binding.Name.text.toString(),binding.password.text.toString(),binding.Contact.text.toString())

            viewModel.state.value = RegistrationState.Loading
        }
        return binding.root
    }

    sealed class RegistrationState {
        object Idle : RegistrationState()
        object Loading : RegistrationState()
        object Added : RegistrationState()
        data class Failed(val message : String) : RegistrationState()
    }


    class RegisterViewModel: ViewModel(){

        val state : MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState.Idle)

        fun adduser(Name:String, Password:String, Phone: Int, email:String){
//            val signedInUser= Firebase.auth.currentUser
//            if (signedInUser != null){
//                val femail = signedInUser.email

            // Create a new user with a first and last name
            val user = mapOf(
                "Name" to Name,
                "Password" to Password,
                "Contact" to Phone,
                "Email" to email
            )

            Firebase.firestore.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("tagged", "DocumentSnapshot added with ID: ${documentReference.id}")

                }
                .addOnFailureListener { e ->
                    Log.w("tagged", "Error adding document", e)
                }
//            }
        }

    }
}