package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.MainFragmentDirections


@BindingAdapter("navigateToDetailScreen")
fun navigateToDetailScreen(constraintLayout: ConstraintLayout, asteroid: Asteroid) {
    val action = MainFragmentDirections.actionShowDetail(asteroid)
    constraintLayout.setOnClickListener {
        it.findNavController().navigate(action)
    }
}
@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("asteroidContentDescription")
fun bindDetailsStatusContentDescription(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.contentDescription = "Asteroid is Hazardous"
    } else {
        imageView.contentDescription = "Asteroid is not Hazardous"
    }
}

@BindingAdapter("loadImageFromUrl")
fun bindPictureOfTheDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    if (pictureOfDay != null) {
        if (pictureOfDay.mediaType == "image") {
            Picasso.with(imageView.context)
                .load(pictureOfDay.url)
                .placeholder(R.drawable.placeholder_picture_of_day)
                .into(imageView)
        }
    }else imageView.setImageResource(R.drawable.placeholder_picture_of_day)
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

