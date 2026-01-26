package com.duoc.ecorentfinal.data.entities

import com.duoc.ecorentfinal.data.model.Herramienta

//objeto con las herramientas

object HerramientasDataSource {
    val herramientas = listOf(
        Herramienta(
            id = 1L,
            nombre = "Taladro Percutor 18V",
            descripcion = "Ideal para perforaciones en diferentes superficies",
            categoria = "Herramientas Eléctricas",
            precioPorDia = 3900.0,
            stock = 5,
            rating = 4.5f,
            disponible = true,
            fabricanteUrl ="https://www.sodimac.cl/sodimac-cl/articulo/140625674/taladro-percutor-inalambrico-13-mm-20-v-brushless1-bateria-2ahcargador25-acc/140625675?kid=goosho_373418&shop=googleShopping&gclsrc=aw.ds&gad_source=1&gad_campaignid=21443067527&gbraid=0AAAAADRw8zupl4EvtQPAzI-M4p92Agyak&gclid=CjwKCAiA7LzLBhAgEiwAjMWzCImBRC9hArglInfeTUHzsW7kWtJnQhpbE8lRby3FjiYXEZxEajolZBoC_18QAvD_BwE"
        ),
        Herramienta(
            id = 2L,
            nombre = "Esmeril Eléctrico 7''",
            descripcion = "Para cortar y pulir metales. Potencia 2000W, velocidad 6000 RPM.",
            categoria = "Herramientas Eléctricas",
            precioPorDia = 5200.0,
            stock = 3,
            rating = 4.2f,
            disponible = true,
            fabricanteUrl ="https://pernoval.cl/productos/14245-esmeril_angular_7_2000w_ga7050_makita-088381606011.html?gad_source=1&gad_campaignid=22412650288&gbraid=0AAAAAphOZvsX5An0jqEcFCSAPaDsizEDZ&gclid=CjwKCAiA7LzLBhAgEiwAjMWzCEq83D3dNyb0TAQ2tSpJ_vDYAQEpsbqCv97T5QSg-x7a35AkmnnmKxoCIJEQAvD_BwE"
        ),
        Herramienta(
            id = 3L,
            nombre = "Soldadora Inversora",
            descripcion = "Para trabajos profesionales de soldadura. Incluye careta y electrodos.",
            categoria = "Soldadura",
            precioPorDia = 8500.0,
            stock = 2,
            rating = 4.7f,
            disponible = true,
            fabricanteUrl ="https://www.sodimac.cl/sodimac-cl/articulo/141512131/soldadora-inverter-160-amp-220v/141512132"

        ),
        Herramienta(
            id = 7L,
            nombre = "Generador a Gasolina 3500W",
            descripcion = "Energía de respaldo para obras y emergencias",
            categoria = "Generadores",
            precioPorDia = 9900.0,
            stock = 2,
            rating = 4.6f,
            fabricanteUrl ="https://www.sodimac.cl/sodimac-cl/b/generador-power-pro-3500?kid=goosho_373419&shop=googleShopping&gclsrc=aw.ds&gad_source=1&gad_campaignid=21447057976&gbraid=0AAAAADRw8ztUhBmB8DQ5pT-3iz4jBi_FW&gclid=CjwKCAiA7LzLBhAgEiwAjMWzCNTTjAE8u0tmkF2HFIZuhpmjP6weHeSeJLPDTqN9uOpCrFaErwYRjRoCaHQQAvD_BwE"
        )
    )
}