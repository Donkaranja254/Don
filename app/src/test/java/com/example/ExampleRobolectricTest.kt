package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.TruceRepository
import com.example.data.local.DishEntity
import com.example.data.local.TruceDatabase
import com.example.model.MenuCategory
import com.example.model.Occasion
import com.example.model.SeatingArea
import com.example.ui.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Faraja Restaurant", appName)
  }

  @Test
  fun `menu repository has nyama choma and mixology items`() {
    val porkChoma = TruceRepository.menuItems.find { it.id == "pork_choma_platter" }
    assertNotNull(porkChoma)
    assertEquals(1800, porkChoma?.priceKes)

    val longIsland = TruceRepository.menuItems.find { it.id == "long_island_tea" }
    assertNotNull(longIsland)
    assertEquals(950, longIsland?.priceKes)
  }

  @Test
  fun `viewModel cart calculation and reservation creation`() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val vm = MainViewModel(app)
    val porkChoma = TruceRepository.menuItems.first { it.id == "pork_choma_platter" }
    vm.addToCart(porkChoma, quantity = 2)

    val state = vm.uiState.value
    assertEquals(2, state.cartItemCount)
    assertEquals(3600, state.cartSubtotalKes)

    val res = vm.createReservation(
      guestName = "Kevin Mwangi",
      guestPhone = "+254 711 123456",
      guestsCount = 6,
      dateText = "Tonight",
      timeSlotText = "8:30 PM",
      seatingArea = SeatingArea.VIP_BALCONY,
      occasion = Occasion.BIRTHDAY,
      specialNotes = "Need firepit nearby"
    )

    assertTrue(res.confirmationCode.startsWith("FARAJA-"))
    assertEquals("Kevin Mwangi", res.guestName)
    assertTrue(vm.uiState.value.showReservationSuccessDialog)
  }

  @Test
  fun `lounge photos gallery has items and valid drawables`() {
    val photos = TruceRepository.loungePhotos
    assertTrue(photos.isNotEmpty())
    val grillPhoto = photos.find { it.category == com.example.model.PhotoCategory.FOOD_GRILL }
    assertNotNull(grillPhoto)
    val terracePhoto = photos.find { it.id == "photo_pergola" }
    assertNotNull(terracePhoto)
    assertEquals(SeatingArea.OUTDOOR_GARDEN, terracePhoto?.linkedSeatingArea)
  }

  @Test
  fun `local room database stores dishes with prices and descriptions`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = TruceDatabase.getInstance(context)
    val dao = db.dishDao()

    val testDish = DishEntity(
      id = "test_flame_ribs",
      name = "Smoky Flame Pork Ribs",
      description = "Marinated in barbecue glaze and slow-roasted over glowing acacia charcoal.",
      priceKes = 2100,
      category = MenuCategory.NYAMA_CHOMA.name,
      tag = "Special Grill",
      isPopular = true,
      portionInfo = "1.2 kg rack",
      drawableRes = R.drawable.img_nyama_choma,
      rating = 5.0,
      isAvailable = true
    )

    dao.insertDish(testDish)

    val retrieved = dao.getDishById("test_flame_ribs")
    assertNotNull(retrieved)
    assertEquals("Smoky Flame Pork Ribs", retrieved?.name)
    assertEquals(2100, retrieved?.priceKes)
    assertEquals(
      "Marinated in barbecue glaze and slow-roasted over glowing acacia charcoal.",
      retrieved?.description
    )

    val all = dao.getAllDishes().first()
    assertTrue(all.any { it.id == "test_flame_ribs" })
  }
}
