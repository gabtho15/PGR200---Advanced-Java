package mappeoppgave;

import mappeoppgave.domene.Innsjoe;
import org.junit.Test;

import java.util.List;
import java.util.Map;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamingoppgaverTest {

    //region Oppgave 1 tester
    @Test
    public void skalFinneAlleSombegynnerPaaC() throws Exception {
        List<Innsjoe> innsjoerSomBegynnerPaaC = Streamingoppgaver.begynnerPaaC();

        assertEquals(1, innsjoerSomBegynnerPaaC.size());
    }

    @Test
    public void skalFinneAlleSomHarMerEnnEttOrd() throws Exception {
        List<Innsjoe> innsjoerSomHarMerEnnEttOrd = Streamingoppgaver.merEnnEttOrd();

        assertEquals(6, innsjoerSomHarMerEnnEttOrd.size());
    }

    @Test
    public void skalFinneAlleSomGrenserTilFlereEnnToLand() throws Exception {
        List<Innsjoe> innsjoerSomGrenserTilFlereEnnToLand = Streamingoppgaver.grenserTilFlereEnnToLand();

        assertEquals(4, innsjoerSomGrenserTilFlereEnnToLand.size());
    }

    @Test
    public void skalFinneAlleSomIEuropaOver10000KmKunEttLand() {
        List<Innsjoe> innsjoerSomIEuropaOver10000KmKunEttLand = Streamingoppgaver.iEuropaOver10000KmKunEttLand();

        assertEquals(1, innsjoerSomIEuropaOver10000KmKunEttLand.size());
    }

    @Test
    public void skalFinneFørsteMedArealPåOver5000Km() throws Exception {
        Innsjoe førsteInnsjoeMedArealOver5000Km = Streamingoppgaver.førsteInnsjoeMedArealOver5000Km();

        assertEquals("Caspian Sea", førsteInnsjoeMedArealOver5000Km.navn());
    }
    //endregion

    //region Oppgave 2 tester
    @Test
    public void skalFinneNavnPaaAlleInnsjoer() throws Exception {
        List<String> navnPaaAlleInnsjoer = Streamingoppgaver.navnPaaAlleInnsjoer();

        assertEquals(36, navnPaaAlleInnsjoer.size());
        assertEquals("Onega", navnPaaAlleInnsjoer.get(16));
        assertEquals("Khanka", navnPaaAlleInnsjoer.get(35));
    }

    @Test
    public void skalFinneNavnPaaAlleInnsjoerMedStoreBokstaver() throws Exception {
        List<String> navnPaaAlleInnsjoerMedStoreBokstaver = Streamingoppgaver.navnPaaAlleInnsjoerMedStoreBokstaver();

        assertEquals(36, navnPaaAlleInnsjoerMedStoreBokstaver.size());
        assertEquals("ONEGA", navnPaaAlleInnsjoerMedStoreBokstaver.get(16));
        assertEquals("KHANKA", navnPaaAlleInnsjoerMedStoreBokstaver.get(35));
    }

    @Test
    public void skalFinneAlleInnsjoerMedAntarktiskSattSomKontinent() throws Exception {
        List<Innsjoe> alleInnsjoerMedAntarktiskSattSomKontinent = Streamingoppgaver.alleInnsjoerMedAntarktiskSattSomKontinent();

        for(int i = 0; i < alleInnsjoerMedAntarktiskSattSomKontinent.size(); i++)
        {
            assertEquals("Antarktisk", alleInnsjoerMedAntarktiskSattSomKontinent.get(i).kontinent());
        }
    }

    @Test
    public void skalFinneForsteInnsjoeMedArealOver500000Km() throws Exception {
        String forsteInnsjoeMedArealOver500000Km = Streamingoppgaver.forsteInnsjoeMedArealOver500000Km();

        assertEquals("Ingen", forsteInnsjoeMedArealOver500000Km);
    }
    //endregion

    //region Oppgave 3 tester
    @Test
    public void skalFinneGjennomsnittligArealPaaInnsjoer() throws Exception {
        Double gjennomsnittligArealPaaInnsjoer = Streamingoppgaver.gjennomsnittligArealPaaInnsjoer();

        assertEquals(30354, gjennomsnittligArealPaaInnsjoer, 1);       // Sammenligner verdi +- 1
    }

    @Test
    public void skalFinneInnsjoeMedStoerstLengde() throws Exception {
        Innsjoe innsjoeMedStoerstLengde = Streamingoppgaver.innsjoeMedStoerstLengde();

        assertEquals("Saimaa", innsjoeMedStoerstLengde.navn());
    }

    @Test
    public void skalFinneInnsjoeMedMinstLengde() throws Exception {
        Innsjoe innsjoeMedMinstLengde = Streamingoppgaver.innsjoeMedMinstLengde();

        assertEquals("Khanka", innsjoeMedMinstLengde.navn());
    }

    @Test
    public void skalFinneInnsjoeMedDybdePåStørreEnnEnTiendelAvLengden() throws Exception {
        Innsjoe innsjoeMedDybdePåStørreEnnEnTiendelAvLengden = Streamingoppgaver.innsjoeMedDybdePåStørreEnnEnTiendelAvLengden();

        assertEquals("Caspian Sea", innsjoeMedDybdePåStørreEnnEnTiendelAvLengden.navn());
    }

    @Test
    public void skalFinneGjennomsnittligLandPerInnsjoe() throws Exception {
        Double gjennomsnittligLandPerInnsjoe = Streamingoppgaver.gjennomsnittligLandPerInnsjoe();

        assertEquals(1.55, gjennomsnittligLandPerInnsjoe, 0.05);
    }

    @Test
    public void skalFinneProduktetAvAlleInnsjoersMaksDybde() throws Exception {
        double produktetAvAlleInnsjoersMaksDybde = Streamingoppgaver.produktetAvAlleInnsjoersMaksDybde();

        assertEquals(1.0915134706186265E79, produktetAvAlleInnsjoersMaksDybde, 1E5);   //1.0^5 i slingringsmål
    }
    //endregion

    //region Oppgave 4 tester
    @Test
    public void skalFinneInnsjoerPerKontinent() throws Exception {
        Map<String, List<Innsjoe>> innsjoerPerKontinent = Streamingoppgaver.innsjoerPerKontinent();

        assertEquals(4, innsjoerPerKontinent.get("Europe").size());     // Hjertelig takk til NotePad++ og dens count funksjon..
        assertEquals(8, innsjoerPerKontinent.get("Asia").size());
        assertEquals(6, innsjoerPerKontinent.get("Africa").size());
        assertEquals(16, innsjoerPerKontinent.get("North America").size());
        assertEquals(1, innsjoerPerKontinent.get("South America").size());
    }

    @Test
    public void skalFinneAntallInnsjoerPerKontinent() throws Exception{
        Map<String, Long> antallInnsjoerPerKontinent = Streamingoppgaver.antallInnsjoerPerKontinent();

        assertEquals(new Long(4), new Long(antallInnsjoerPerKontinent.get("Europe")));
        assertEquals(new Long(8), new Long(antallInnsjoerPerKontinent.get("Asia")));
        assertEquals(new Long(6), new Long(antallInnsjoerPerKontinent.get("Africa")));
        assertEquals(new Long(16), new Long(antallInnsjoerPerKontinent.get("North America")));
        assertEquals(new Long(1), new Long(antallInnsjoerPerKontinent.get("South America")));
    }

    @Test
    public void skalFinneNavnPaaAlleInnsjoerIEnString() throws Exception {
        String navnPaaAlleInnsjoerIEnString = Streamingoppgaver.navnPaaAlleInnsjoerIEnString();
        long antallInnsjoerILista = navnPaaAlleInnsjoerIEnString.chars().filter(s -> s =='|').count();

        assertTrue(navnPaaAlleInnsjoerIEnString.startsWith("Caspian"));
        assertTrue(navnPaaAlleInnsjoerIEnString.endsWith("Khanka"));
        assertEquals("Sjekker om det er korrekt antall '|' i lista", 35, antallInnsjoerILista);
    }

    @Test
    public void skalFinneGjennomsnittligArealPaaInnsjoerMedAveragingDouble() throws Exception{
        Double gjennomsnittligArealPaaInnsjoerMedAveragingDouble
                = Streamingoppgaver.gjennomsnittligArealPaaInnsjoerMedAveragingDouble();

        assertEquals(30354, gjennomsnittligArealPaaInnsjoerMedAveragingDouble, 1);
    }

    @Test
    public void skalFinneMapMedInnsjoerDeltIToDybdeKategorier() throws Exception{
        Map<Boolean, List<Innsjoe>> map = Streamingoppgaver.mapMedInnsjoerDeltIToDybdeKategorier();

        assertEquals(7, map.get(true).size());      //Over 500m
        assertEquals(29, map.get(false).size());    //500m og under
    }
    //endregion

    //region Oppgave 5 tester
    @Test
    public void skalFinneAlleLandSomErRepresentert() throws Exception {
        List<String> alleLandSomErRepresentert = Streamingoppgaver.alleLandSomErRepresentert();

        assertEquals(57, alleLandSomErRepresentert.size());
    }

    @Test
    public void skalFinneAlleLandSomErRepresentertUtenDuplikater() throws Exception {
        List<String> alleLandSomErRepresentertUtenDuplikater = Streamingoppgaver.alleLandSomErRepresentertUtenDuplikater();

        assertEquals(25, alleLandSomErRepresentertUtenDuplikater.size());
    }

    @Test
    public void skalFinneAlleALandSomErRepresentertMedAntallForekomster() throws Exception {
        Map<String, Long> alleALandSomErRepresentertMedAntallForekomster
                = Streamingoppgaver.alleLandSomErRepresentertMedAntallForekomster();

        assertEquals(25, alleALandSomErRepresentertMedAntallForekomster.size());
    }
    //endregion
}