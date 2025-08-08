package com.iti.myapplicationbnv.presentation.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iti.myapplicationbnv.R
import com.iti.myapplicationbnv.presentation.auth.AuthActivity
import com.iti.myapplicationbnv.data.data.sharedpref.sharedpreferences

class AboutCreatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about_creator, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_about_creator)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "About Creator"
        }

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"

        val menuHost = requireActivity() as MenuHost

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_sign_out -> {
                        val shared = sharedpreferences(requireContext())
                        shared.logout()
                        val intent = Intent(requireContext(), AuthActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("openLoginDirect", true)
                        startActivity(intent)

                        true
                    }

                    R.id.menu_about_creator -> {
                        findNavController().navigate(R.id.action_homeFragment_to_aboutCreatorFragment)
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner)
    }


}