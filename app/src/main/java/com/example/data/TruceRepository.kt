package com.example.data

import com.example.R
import com.example.model.*

object TruceRepository {
    const val RESTAURANT_NAME = "Faraja Restaurant"
    const val PHONE_NUMBER = "+254110051967"
    const val DISPLAY_PHONE = "+254 110 051967"
    const val LOCATION_NAME = "Ruiru Town, Kiambu County, Kenya"
    const val LOCATION_LANDMARK = "Ruiru Town, Next to National Bank"
    const val GOOGLE_MAPS_URL = "https://maps.app.goo.gl/A8mtV59QBycp7nHt6"
    const val GOOGLE_MAPS_QUERY = "Faraja+Restaurant+Ruiru+Kiambu"
    const val WHATSAPP_INTENT_URL = "https://wa.me/254110051967"

    val menuItems: List<MenuItem> = listOf(
        // Nyama Choma & Grills
        MenuItem(
            id = "pork_choma_platter",
            name = "Platter of Pork Choma",
            description = "Succulent charcoal-grilled pork ribs & chops seasoned with house herbs. Served with fresh kachumbari and choice of ugali or fries.",
            priceKes = 1800,
            category = MenuCategory.NYAMA_CHOMA,
            tag = "Popular",
            isPopular = true,
            portionInfo = "1 kg platter (Serves 2-3)",
            drawableRes = R.drawable.img_nyama_choma,
            rating = 4.9
        ),
        MenuItem(
            id = "mbuzi_choma",
            name = "Succulent Mbuzi Choma",
            description = "Prime cuts of fresh highland goat slow-roasted over open charcoal coals until tender and caramelized. Served with kachumbari and chili dip.",
            priceKes = 1900,
            category = MenuCategory.NYAMA_CHOMA,
            tag = "Fresh Daily",
            isPopular = true,
            portionInfo = "1 kg slow-roasted goat",
            drawableRes = R.drawable.img_nyama_choma,
            rating = 5.0
        ),
        MenuItem(
            id = "whole_kienyeji_choma",
            name = "Whole Kienyeji Chicken Choma",
            description = "Local free-range chicken marinated in rosemary, garlic, and ginger, grilled to golden crisp perfection over glowing coals.",
            priceKes = 2200,
            category = MenuCategory.NYAMA_CHOMA,
            tag = "Chef's Special",
            portionInfo = "Full organic chicken",
            drawableRes = R.drawable.img_roast_chicken,
            rating = 4.8
        ),
        MenuItem(
            id = "beef_choma_skewers",
            name = "Prime Beef Choma Skewers",
            description = "Tender prime beef chunks threaded with bell peppers and red onions, basted in spiced barbecue glaze.",
            priceKes = 1200,
            category = MenuCategory.NYAMA_CHOMA,
            portionInfo = "4 large skewers with dip",
            drawableRes = R.drawable.img_nyama_choma,
            rating = 4.7
        ),
        MenuItem(
            id = "truce_carnivore_board",
            name = "Faraja Carnivore Feast Board",
            description = "The ultimate sharing board: 1kg Pork choma, 1kg Mbuzi choma, Grilled chicken wings, choma sausages, fries, mukimo, and kachumbari.",
            priceKes = 4600,
            category = MenuCategory.NYAMA_CHOMA,
            tag = "Feast Pick",
            isPopular = true,
            portionInfo = "Serves 4 - 6 people",
            drawableRes = R.drawable.img_nyama_choma,
            rating = 5.0
        ),

        // Wet & Dry Fry
        MenuItem(
            id = "wet_fry_mbuzi",
            name = "Wet Fry Mbuzi",
            description = "Rich traditional stew preparation with braised goat cuts simmered in caramelized onions, ripe tomatoes, ginger, and fresh dhania.",
            priceKes = 1600,
            category = MenuCategory.WET_DRY_FRY,
            tag = "Fresh Daily",
            isPopular = true,
            portionInfo = "1 kg rich stew portion",
            drawableRes = R.drawable.img_wet_fry,
            rating = 4.9
        ),
        MenuItem(
            id = "dry_fry_pork",
            name = "Spicy Dry Fry Pork",
            description = "Crispy pan-seared pork tossed with roasted green chilies, sweet bell peppers, scallions, and freshly ground coriander.",
            priceKes = 1600,
            category = MenuCategory.WET_DRY_FRY,
            tag = "Hot & Sizzling",
            portionInfo = "1 kg skillet fry",
            drawableRes = R.drawable.img_wet_fry,
            rating = 4.8
        ),
        MenuItem(
            id = "spicy_wings",
            name = "Faraja Glazed Fire Wings",
            description = "Crispy chicken wings tossed in homemade sweet chili & scotch bonnet reduction with sesame crunch.",
            priceKes = 950,
            category = MenuCategory.WET_DRY_FRY,
            portionInfo = "8 jumbo wings",
            drawableRes = R.drawable.img_roast_chicken,
            rating = 4.7
        ),
        MenuItem(
            id = "beef_wet_fry",
            name = "Traditional Wet Fry Beef",
            description = "Tender beef fillet strips pan-fried with roasted capsicum, garlic gravy, and fresh garden herbs.",
            priceKes = 1400,
            category = MenuCategory.WET_DRY_FRY,
            portionInfo = "1 kg portion",
            drawableRes = R.drawable.img_wet_fry,
            rating = 4.6
        ),

        // Sides
        MenuItem(
            id = "side_kachumbari",
            name = "Signature Fresh Kachumbari",
            description = "Finely diced vine tomatoes, sweet red onions, fresh coriander, squeeze of coastal lime, and optional green chili.",
            priceKes = 150,
            category = MenuCategory.SIDES,
            portionInfo = "Fresh bowl",
            drawableRes = R.drawable.img_sides_platter,
            rating = 4.9
        ),
        MenuItem(
            id = "side_mukimo",
            name = "Traditional Mukimo",
            description = "Nutritious traditional mashed potatoes, tender pumpkin leaves, maize kernels, and buttered onions.",
            priceKes = 300,
            category = MenuCategory.SIDES,
            tag = "Traditional",
            portionInfo = "Generous bowl",
            drawableRes = R.drawable.img_sides_platter,
            rating = 4.8
        ),
        MenuItem(
            id = "side_ugali",
            name = "Hot Steaming Ugali",
            description = "Traditional white maize meal cake, fluffy and comforting — the quintessential companion for nyama choma.",
            priceKes = 150,
            category = MenuCategory.SIDES,
            portionInfo = "Full plate",
            drawableRes = R.drawable.img_sides_platter,
            rating = 4.8
        ),
        MenuItem(
            id = "side_masala_fries",
            name = "Crispy Masala Fries",
            description = "Golden potato fries tossed in aromatic spicy tomato masala sauce with chopped coriander and garlic mayo.",
            priceKes = 350,
            category = MenuCategory.SIDES,
            tag = "Popular Side",
            portionInfo = "Basket",
            drawableRes = R.drawable.img_sides_platter,
            rating = 4.7
        ),

        // Cocktails
        MenuItem(
            id = "long_island_tea",
            name = "Long Island Ice Tea",
            description = "Vodka, silver tequila, light rum, dry gin, triple sec, citrus squeeze, and splash of cola over crushed ice.",
            priceKes = 950,
            category = MenuCategory.COCKTAILS,
            tag = "Top Drink",
            isPopular = true,
            portionInfo = "Tall cocktail glass",
            drawableRes = R.drawable.img_cocktails,
            rating = 5.0
        ),
        MenuItem(
            id = "classic_whiskey_sour",
            name = "Classic Whiskey Sour",
            description = "Jack Daniel's Old No. 7, freshly squeezed lime juice, homemade cane sugar syrup, and aromatic bitters.",
            priceKes = 900,
            category = MenuCategory.COCKTAILS,
            tag = "Bar Choice",
            isPopular = true,
            portionInfo = "Rocks glass with orange peel",
            drawableRes = R.drawable.img_cocktails,
            rating = 4.9
        ),
        MenuItem(
            id = "kiambu_sunset",
            name = "Faraja Sunset Cocktail",
            description = "Captain Morgan spiced rum, passion fruit purée, fresh mango juice, grenadine, topped with sparkling water.",
            priceKes = 850,
            category = MenuCategory.COCKTAILS,
            tag = "House Signature",
            portionInfo = "Hurricane glass",
            drawableRes = R.drawable.img_cocktails,
            rating = 4.8
        ),
        MenuItem(
            id = "nairobi_mule",
            name = "Nairobi Copper Mule",
            description = "Premium vodka, fiery pressed ginger extract, lime wedge, topped with cold ginger beer in a frosted copper mug.",
            priceKes = 850,
            category = MenuCategory.COCKTAILS,
            portionInfo = "Chilled copper mug",
            drawableRes = R.drawable.img_cocktails,
            rating = 4.7
        ),

        // Beers & Ciders
        MenuItem(
            id = "tusker_lager",
            name = "Tusker Lager (Cold 500ml)",
            description = "Kenya's iconic pale lager brewed from 100% African ingredients. Served ice-cold.",
            priceKes = 350,
            category = MenuCategory.BEERS_CIDERS,
            portionInfo = "500ml bottle",
            drawableRes = R.drawable.img_chilled_beers,
            rating = 4.9
        ),
        MenuItem(
            id = "tusker_cider",
            name = "Tusker Cider",
            description = "Crisp and delightfully refreshing premium apple cider.",
            priceKes = 400,
            category = MenuCategory.BEERS_CIDERS,
            portionInfo = "500ml bottle",
            drawableRes = R.drawable.img_chilled_beers,
            rating = 4.8
        ),
        MenuItem(
            id = "heineken_cold",
            name = "Heineken Premium Cold",
            description = "Pure malt European lager served extra chilled.",
            priceKes = 450,
            category = MenuCategory.BEERS_CIDERS,
            portionInfo = "330ml bottle",
            drawableRes = R.drawable.img_chilled_beers,
            rating = 4.7
        ),
        MenuItem(
            id = "guinness_extra",
            name = "Guinness Foreign Extra Stout",
            description = "Rich, dark, and complex roasted barley character.",
            priceKes = 400,
            category = MenuCategory.BEERS_CIDERS,
            portionInfo = "500ml bottle",
            drawableRes = R.drawable.img_chilled_beers,
            rating = 4.8
        ),

        // Whiskey & Spirits
        MenuItem(
            id = "jack_daniels",
            name = "Jack Daniel's Old No. 7",
            description = "Charcoal-mellowed Tennessee sour mash whiskey. Available per double tot or full 750ml bottle.",
            priceKes = 450,
            category = MenuCategory.WHISKEY_SPIRITS,
            tag = "Bar Favorite",
            portionInfo = "Double tot (Bottle KES 6,500)",
            drawableRes = R.drawable.img_whiskey_spirits,
            rating = 4.9
        ),
        MenuItem(
            id = "jw_black_label",
            name = "Johnnie Walker Black Label",
            description = "Iconic 12-year-old blended Scotch whiskey with deep smoke, dark fruits, and sweet vanilla notes.",
            priceKes = 500,
            category = MenuCategory.WHISKEY_SPIRITS,
            portionInfo = "Double tot (Bottle KES 7,500)",
            drawableRes = R.drawable.img_whiskey_spirits,
            rating = 4.8
        ),
        MenuItem(
            id = "glenfiddich_12",
            name = "Glenfiddich 12 YO Single Malt",
            description = "Distinctive fresh pear notes and subtle oak from Speyside, Scotland.",
            priceKes = 700,
            category = MenuCategory.WHISKEY_SPIRITS,
            portionInfo = "Double tot (Bottle KES 11,000)",
            drawableRes = R.drawable.img_whiskey_spirits,
            rating = 4.9
        ),

        // Non-Alcoholic
        MenuItem(
            id = "fresh_passion_mint",
            name = "Fresh Passion & Mint Cooler",
            description = "Freshly pressed passion juice, bruised garden mint, crushed ice, and lemon soda.",
            priceKes = 350,
            category = MenuCategory.NON_ALCOHOLIC,
            portionInfo = "Chilled tall glass",
            drawableRes = R.drawable.img_cocktails,
            rating = 4.8
        ),
        MenuItem(
            id = "virgin_mojito",
            name = "Virgin Classic Mojito",
            description = "Muddled fresh limes, brown cane sugar, mint leaves, topped with sparkling club soda.",
            priceKes = 450,
            category = MenuCategory.NON_ALCOHOLIC,
            portionInfo = "Highball glass",
            drawableRes = R.drawable.img_cocktails,
            rating = 4.7
        ),
        MenuItem(
            id = "red_bull",
            name = "Red Bull Energy Drink",
            description = "Vitalizes body and mind for lively restaurant dining.",
            priceKes = 350,
            category = MenuCategory.NON_ALCOHOLIC,
            portionInfo = "250ml can",
            drawableRes = R.drawable.img_cocktails,
            rating = 4.6
        )
    )

    val events: List<LoungeEvent> = listOf(
        LoungeEvent(
            id = "ev_fri",
            title = "Afro-Beats & Grill Fridays",
            scheduleDay = "Every Friday",
            time = "6:00 PM – Late",
            headline = "Top resident DJs on the decks, sizzling open grills, and cocktail specials at Faraja.",
            badge = "Weekend Special",
            perk = "Happy Hour on Refreshments till 9 PM"
        ),
        LoungeEvent(
            id = "ev_sun",
            title = "Sunday Family Choma Live",
            scheduleDay = "Every Sunday",
            time = "1:00 PM – 10:00 PM",
            headline = "Relaxed family afternoon dining, 1kg Choma platters, and outdoor garden breeze in Ruiru.",
            badge = "Family Sunday",
            perk = "Complimentary Kachumbari & Ugali with 2kg Choma"
        ),
        LoungeEvent(
            id = "ev_match",
            title = "EPL & Champions League Live",
            scheduleDay = "Matchdays",
            time = "Live Match Times",
            headline = "Experience high-definition football on giant screens with matchday food offers.",
            badge = "Sports Dining",
            perk = "Bucket Drinks Offers (5 for KES 1,600)"
        ),
        LoungeEvent(
            id = "ev_thu",
            title = "Afro-Grill & Mixology",
            scheduleDay = "Every Thursday",
            time = "7:00 PM – Late",
            headline = "Signature cocktail tasting and delicious wet fry specials.",
            badge = "Special Night",
            perk = "Special discounts on grill platters"
        )
    )

    val customerReviews: List<CustomerReview> = listOf(
        CustomerReview(
            id = "rev_1",
            author = "Brian Mwangi",
            rating = 5,
            comment = "Best pork choma in Ruiru Town hands down! Tender, juicy, and the kachumbari had that perfect kick. Very convenient location next to National Bank.",
            date = "Yesterday",
            favoriteItem = "Platter of Pork Choma"
        ),
        CustomerReview(
            id = "rev_2",
            author = "Sarah K.",
            rating = 5,
            comment = "Faraja Restaurant is our family regular now. Love the generous portions, fast service, and welcoming atmosphere in Ruiru.",
            date = "3 days ago",
            favoriteItem = "Succulent Mbuzi Choma"
        ),
        CustomerReview(
            id = "rev_3",
            author = "Dennis Otieno",
            rating = 5,
            comment = "The wet fry mbuzi here is unmatched. Tender meat, rich gravy, and hot steaming ugali. Excellent place to dine with friends.",
            date = "Last week",
            favoriteItem = "Wet Fry Mbuzi"
        )
    )

    val loungePhotos: List<LoungePhoto> = listOf(
        LoungePhoto(
            id = "photo_pork_choma",
            title = "Pork Choma Platter",
            subtitle = "Flame Grilled • Tender Ribs",
            description = "Slow charcoal-roasted pork chops and ribs seasoned with traditional Kenyan spices, served alongside fresh diced kachumbari and steaming ugali.",
            category = PhotoCategory.FOOD_GRILL,
            tag = "Signature Grill",
            drawableRes = R.drawable.img_nyama_choma,
            linkedMenuItemId = "pork_choma_platter"
        ),
        LoungePhoto(
            id = "photo_mbuzi",
            title = "Succulent Mbuzi Choma",
            subtitle = "Fresh Daily • Caramelized Crust",
            description = "Prime goat cuts grilled to perfection over glowing charcoal embers, offering juicy tenderness with fiery pili pili dip.",
            category = PhotoCategory.FOOD_GRILL,
            tag = "Highland Goat",
            drawableRes = R.drawable.img_nyama_choma,
            linkedMenuItemId = "mbuzi_choma"
        ),
        LoungePhoto(
            id = "photo_cocktails",
            title = "Bar & Mixology",
            subtitle = "Long Island & Whiskey Sour",
            description = "Handcrafted with premium spirits, fresh citrus infusions, and aromatic bitters, served extra chilled over artisanal ice.",
            category = PhotoCategory.COCKTAILS_BAR,
            tag = "Bar Choice",
            drawableRes = R.drawable.img_cocktails,
            linkedMenuItemId = "long_island_tea"
        ),
        LoungePhoto(
            id = "photo_pergola",
            title = "Garden Terrace & Dining",
            subtitle = "Open-Air Terraces • Family Friendly",
            description = "Enjoy relaxed open-air dining at Faraja Restaurant, featuring spacious garden seating in Ruiru Town.",
            category = PhotoCategory.ATMOSPHERE_LOUNGE,
            tag = "Garden Vibe",
            drawableRes = R.drawable.img_garden_terrace,
            linkedSeatingArea = SeatingArea.OUTDOOR_GARDEN
        ),
        LoungePhoto(
            id = "photo_main_lounge",
            title = "Faraja Restaurant Dining Hall",
            subtitle = "Warm Ambiance • Great Service",
            description = "Immerse yourself in our welcoming restaurant ambiance with warm lighting, quality seating, and friendly attentive staff.",
            category = PhotoCategory.ATMOSPHERE_LOUNGE,
            tag = "Main Hall",
            drawableRes = R.drawable.img_hero_lounge,
            linkedSeatingArea = SeatingArea.MAIN_LOUNGE
        ),
        LoungePhoto(
            id = "photo_sunset_drink",
            title = "Faraja Sunset Cooler",
            subtitle = "Tropical Refreshment",
            description = "Tropical mango purée, fresh passion extract, and citrus splash celebrating the vibrant Kenyan evening.",
            category = PhotoCategory.COCKTAILS_BAR,
            tag = "Signature Mix",
            drawableRes = R.drawable.img_cocktails,
            linkedMenuItemId = "kiambu_sunset"
        ),
        LoungePhoto(
            id = "photo_carnivore",
            title = "Faraja Carnivore Sharing Feast",
            subtitle = "Serves 4 to 6 Guests",
            description = "The ultimate Faraja party feast loaded with 1kg pork choma, 1kg goat choma, grilled wings, choma sausages, masala fries, and mukimo.",
            category = PhotoCategory.FOOD_GRILL,
            tag = "Feast Platter",
            drawableRes = R.drawable.img_nyama_choma,
            linkedMenuItemId = "truce_carnivore_board"
        ),
        LoungePhoto(
            id = "photo_night_terrace",
            title = "Welcoming Dining Experience",
            subtitle = "Ruiru Town • Next to National Bank",
            description = "Whether for breakfast, hearty lunch, or relaxed evening dinner with family and friends, Faraja Restaurant welcomes you.",
            category = PhotoCategory.ATMOSPHERE_LOUNGE,
            tag = "Ruiru Town",
            drawableRes = R.drawable.img_garden_terrace,
            linkedSeatingArea = SeatingArea.OUTDOOR_GARDEN
        )
    )
}
