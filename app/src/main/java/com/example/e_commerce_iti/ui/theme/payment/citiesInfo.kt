package com.example.e_commerce_iti.ui.theme.payment

val egyptianCities = listOf(
    "Cairo",
    "Alexandria",
    "Giza",
    "Shubra El Kheima",
    "Port Said",
    "Suez",
    "Luxor",
    "Mansoura",
    "Tanta",
    "Asyut",
    "Ismailia",
    "Faiyum",
    "Zagazig",
    "Damietta",
    "Aswan",
    "Minya",
    "Beni Suef",
    "Hurghada",
    "Qena",
    "Sohag"
)
val egyptGovernoratesAndAreas = mapOf(
    "Cairo" to listOf(
        "Heliopolis", "Nasr City", "Maadi", "Zamalek", "Garden City", "Downtown",
        "Shubra", "New Cairo", "Helwan", "El Rehab", "5th Settlement", "Ain Shams"
    ),
    "Alexandria" to listOf(
        "El Montaza", "Raml Station", "Sidi Bishr", "Smouha", "Stanley", "Gleem",
        "Borg El Arab", "Agamy", "Miami", "Kafr Abdu"
    ),
    "Giza" to listOf(
        "Dokki", "Mohandessin", "Haram", "Faisal", "6th of October City",
        "Sheikh Zayed", "Imbaba", "Agouza", "Bulaq"
    ),
    "Port Said" to listOf("El Dawahy", "El Arab", "El Manakh", "Mubarak"),
    "Suez" to listOf("El Arbaeen", "El Ganayen", "Ataqah", "Port Tawfiq"),
    "Luxor" to listOf("Karnak", "Al Bayadia", "Armant", "Esna"),
    "Aswan" to listOf("Edfu", "Kom Ombo", "Daraw", "Abu Simbel", "Aswan City"),
    "Sharkia" to listOf("Zagazig", "Abu Hammad", "Belbeis", "Minya El Qamh", "10th of Ramadan City"),
    "Dakahlia" to listOf("Mansoura", "Mit Ghamr", "Talkha", "Belqas", "Gamasa", "Manzala"),
    "Ismailia" to listOf("Fayed", "Al Qantara", "Tal El Kebir", "Abu Sweir"),
    "Faiyum" to listOf("Ibshaway", "Sinnuris", "Tamiyah", "Yusuf El Seddik", "Faiyum City"),
    "Beni Suef" to listOf("Biba", "El Wasta", "Nasser", "Ihnasiya", "Beni Suef City"),
    "Minya" to listOf("Samalut", "Maghagha", "Mallawi", "Abu Qurqas", "Minya City"),
    "Sohag" to listOf("Akhmim", "Tahta", "Girga", "El Balyana", "Sohag City"),
    "Qena" to listOf("Qus", "Nag Hammadi", "Deshna", "Farshut", "Qena City"),
    "Asyut" to listOf("Dairut", "Manfalut", "Abnoub", "El Badari", "Asyut City"),
    "Damietta" to listOf("Ras El Bar", "New Damietta", "Faraskur", "Zarqa"),
    "Beheira" to listOf("Damanhour", "Kafr El Dawwar", "Abu Hummus", "Rashid", "Edko"),
    "Kafr El Sheikh" to listOf("Desouk", "Fuwah", "Sidi Salem", "Balteem", "Kafr El Sheikh City"),
    "Gharbia" to listOf("Tanta", "El Mahalla El Kubra", "Kafr El Zayat", "Samanoud"),
    "Monufia" to listOf("Shebin El Kom", "Menouf", "Sers El Layan", "Ashmoun", "Sadat City"),
    "Red Sea" to listOf("Hurghada", "Safaga", "Quseir", "Marsa Alam", "El Gouna"),
    "New Valley" to listOf("Kharga", "Dakhla", "Farafra", "Baris", "Mut"),
    "Matruh" to listOf("Marsa Matruh", "Sidi Barrani", "El Alamein", "Siwa Oasis"),
    "North Sinai" to listOf("Arish", "Bir al-Abd", "Sheikh Zuweid", "Rafah"),
    "South Sinai" to listOf("Sharm El Sheikh", "Dahab", "Taba", "Nuweiba", "Saint Catherine")
)

val egyptGovernoratesAndAreasWithZip = mapOf(
    "Cairo" to listOf(
        "Heliopolis" to "11341",
        "Nasr City" to "11765",
        "Maadi" to "11431",
        "Zamalek" to "11211",
        "Garden City" to "11519",
        "Downtown" to "11511",
        "Shubra" to "11622",
        "New Cairo" to "11865",
        "Helwan" to "11790",
        "El Rehab" to "11841",
        "5th Settlement" to "11835",
        "Ain Shams" to "11777"
    ),
    "Alexandria" to listOf(
        "El Montaza" to "21919",
        "Raml Station" to "21563",
        "Sidi Bishr" to "21611",
        "Smouha" to "21575",
        "Stanley" to "21526",
        "Gleem" to "21411",
        "Borg El Arab" to "21934",
        "Agamy" to "21928",
        "Miami" to "21614",
        "Kafr Abdu" to "21561"
    ),
    "Giza" to listOf(
        "Dokki" to "12311",
        "Mohandessin" to "12411",
        "Haram" to "12556",
        "Faisal" to "12572",
        "6th of October City" to "12566",
        "Sheikh Zayed" to "12588",
        "Imbaba" to "12651",
        "Agouza" to "12652",
        "Bulaq" to "12613"
    ),
    "Port Said" to listOf(
        "El Dawahy" to "42511",
        "El Arab" to "42512",
        "El Manakh" to "42513",
        "Mubarak" to "42515"
    ),
    "Suez" to listOf(
        "El Arbaeen" to "43511",
        "El Ganayen" to "43512",
        "Ataqah" to "43514",
        "Port Tawfiq" to "43516"
    ),
    "Luxor" to listOf(
        "Karnak" to "85951",
        "Al Bayadia" to "85952",
        "Armant" to "85923",
        "Esna" to "85955"
    ),
    "Aswan" to listOf(
        "Edfu" to "81735",
        "Kom Ombo" to "81611",
        "Daraw" to "81632",
        "Abu Simbel" to "81736",
        "Aswan City" to "81511"
    ),
    "Sharkia" to listOf(
        "Zagazig" to "44511",
        "Abu Hammad" to "44661",
        "Belbeis" to "44611",
        "Minya El Qamh" to "44671",
        "10th of Ramadan City" to "44635"
    ),
    "Dakahlia" to listOf(
        "Mansoura" to "35511",
        "Mit Ghamr" to "35611",
        "Talkha" to "35721",
        "Belqas" to "35631",
        "Gamasa" to "35717",
        "Manzala" to "35641"
    ),
    "Ismailia" to listOf(
        "Fayed" to "41511",
        "Al Qantara" to "41621",
        "Tal El Kebir" to "41631",
        "Abu Sweir" to "41512"
    ),
    "Faiyum" to listOf(
        "Ibshaway" to "63611",
        "Sinnuris" to "63711",
        "Tamiyah" to "63811",
        "Yusuf El Seddik" to "63871",
        "Faiyum City" to "63511"
    ),
    "Beni Suef" to listOf(
        "Biba" to "62731",
        "El Wasta" to "62811",
        "Nasser" to "62611",
        "Ihnasiya" to "62621",
        "Beni Suef City" to "62511"
    ),
    "Minya" to listOf(
        "Samalut" to "61611",
        "Maghagha" to "61711",
        "Mallawi" to "61671",
        "Abu Qurqas" to "61771",
        "Minya City" to "61511"
    ),
    "Sohag" to listOf(
        "Akhmim" to "82715",
        "Tahta" to "82811",
        "Girga" to "82511",
        "El Balyana" to "82611",
        "Sohag City" to "82511"
    ),
    "Qena" to listOf(
        "Qus" to "83731",
        "Nag Hammadi" to "83711",
        "Deshna" to "83611",
        "Farshut" to "83751",
        "Qena City" to "83511"
    ),
    "Asyut" to listOf(
        "Dairut" to "71611",
        "Manfalut" to "71711",
        "Abnoub" to "71621",
        "El Badari" to "71731",
        "Asyut City" to "71511"
    ),
    "Damietta" to listOf(
        "Ras El Bar" to "34717",
        "New Damietta" to "34719",
        "Faraskur" to "34711",
        "Zarqa" to "34714"
    ),
    "Beheira" to listOf(
        "Damanhour" to "22511",
        "Kafr El Dawwar" to "22611",
        "Abu Hummus" to "22631",
        "Rashid" to "22712",
        "Edko" to "22731"
    ),
    "Kafr El Sheikh" to listOf(
        "Desouk" to "33611",
        "Fuwah" to "33721",
        "Sidi Salem" to "33821",
        "Balteem" to "33951",
        "Kafr El Sheikh City" to "33511"
    ),
    "Gharbia" to listOf(
        "Tanta" to "31511",
        "El Mahalla El Kubra" to "31911",
        "Kafr El Zayat" to "31611",
        "Samanoud" to "31621"
    ),
    "Monufia" to listOf(
        "Shebin El Kom" to "32611",
        "Menouf" to "32951",
        "Sers El Layan" to "32711",
        "Ashmoun" to "32811",
        "Sadat City" to "32821"
    ),
    "Red Sea" to listOf(
        "Hurghada" to "84511",
        "Safaga" to "84711",
        "Quseir" to "84712",
        "Marsa Alam" to "84721",
        "El Gouna" to "84513"
    ),
    "New Valley" to listOf(
        "Kharga" to "72511",
        "Dakhla" to "72715",
        "Farafra" to "72731",
        "Baris" to "72611",
        "Mut" to "72717"
    ),
    "Matruh" to listOf(
        "Marsa Matruh" to "51511",
        "Sidi Barrani" to "51711",
        "El Alamein" to "51718",
        "Siwa Oasis" to "51714"
    ),
    "North Sinai" to listOf(
        "Arish" to "45611",
        "Bir al-Abd" to "45621",
        "Sheikh Zuweid" to "45612",
        "Rafah" to "45613"
    ),
    "South Sinai" to listOf(
        "Sharm El Sheikh" to "46619",
        "Dahab" to "46617",
        "Taba" to "46621",
        "Nuweiba" to "46618",
        "Saint Catherine" to "46616"
    )
)
