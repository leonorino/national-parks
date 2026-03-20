package me.leonorino.nationalparks.model

import me.leonorino.nationalparks.R

enum class USState(val abbreviation: String, val fullNameResId: Int) {
    AL("AL", R.string.AL), AK("AK", R.string.AK), AZ("AZ", R.string.AZ), AR("AR", R.string.AR),
    CA("CA", R.string.CA), CO("CO", R.string.CO), CT("CT", R.string.CT), DE("DE", R.string.DE),
    FL("FL", R.string.FL), GA("GA", R.string.GA), HI("HI", R.string.HI), ID("ID", R.string.ID),
    IL("IL", R.string.IL), IN("IN", R.string.IN), IA("IA", R.string.IA), KS("KS", R.string.KS),
    KY("KY", R.string.KY), LA("LA", R.string.LA), ME("ME", R.string.ME), MD("MD", R.string.MD),
    MA("MA", R.string.MA), MI("MI", R.string.MI), MN("MN", R.string.MN), MS("MS", R.string.MS),
    MO("MO", R.string.MO), MT("MT", R.string.MT), NE("NE", R.string.NE), NV("NV", R.string.NV),
    NH("NH", R.string.NH), NJ("NJ", R.string.NJ), NM("NM", R.string.NM), NY("NY", R.string.NY),
    NC("NC", R.string.NC), ND("ND", R.string.ND), OH("OH", R.string.OH), OK("OK", R.string.OK),
    OR("OR", R.string.OR), PA("PA", R.string.PA), RI("RI", R.string.RI), SC("SC", R.string.SC),
    SD("SD", R.string.SD), TN("TN", R.string.TN), TX("TX", R.string.TX), UT("UT", R.string.UT),
    VT("VT", R.string.VT), VA("VA", R.string.VA), WA("WA", R.string.WA), WV("WV", R.string.WV),
    WI("WI", R.string.WI), WY("WY", R.string.WY),
    DC("DC", R.string.DC), AS("AS", R.string.AS), VI("VI", R.string.VI);
}
