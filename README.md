Unknown-Revengers
=================

T4 - Layout Analyze

Scopul acestui proiect este de a crea un executabil care sa permita corectia erorilor in ceea ce priveste gruparea caracterelor in randuri si a randurilor in blocuri de text, a paginarii, si a continutului text propriu-zis.

###Workflow-ul tipic este urmatorul:###

- Utilizatorul incarca un <a href="develop/execs/input/input.xml">fisier XML</a> asupra caruia a fost executata analiza de layout sau fie incarca o imagine si selecteaza ce analizator de layout sa foloseasca din <a href="develop/execs/xml_schemas/mock_layout.xsd">cele disponibile</a>. Un analizator este defapt un task definit in xsd. 
- Dupa ce se alege analizatorul, se incarca imaginea. Cred ca se afiseaza imaginea si pe deasupra ei pot fi ilustrate componentele imaginii document: litere, randuri de litere si blocuri text; in functie de selectia utilizatorului, pot fi vizualizate toate cele 3 niveluri, sau doar anumite categorii.
- Daca asupra textului nu s-a executat analiza OCR (initial nu este facuta analiza OCR pentru niciun bloc), se poate cere analiza aceasta selectand analiza OCR, fie pe toata imaginea, fie doar pe anumite randuri din imagine
- Rezultatele analizei OCR pot fi corectate selectand linia dorita si modificand textul atasat care trebuie sa fie atasat intr-o caseta text alaturata (ceva gen click pe block si apare un popup cu textul)
- Daca niciunul din blocurile text nu este identificat ca fiind numar de pagina, se poate fie rula modulul de numerotare a paginii (inexistent momentan cred), fie sa se impuna ca unul din blocuri sa reprezinte numarul de pagina. Pentru ca blocul de pagina contine randuri (de fapt un singur rand la o detectie corecta), numarul poate fi detectat prin OCR sau introdus manual
- Pe langa aceste actiuni, trebuie sa fie posibila redimensionarea unirea/spargerea blocurilor text, unirea/spargerea liniilor de litere
- Rezultatul va fi un fisier XML care descrie layout-ul unei imaginii, conform schemei (ca <a href="develop/execs/input/input.xml">aici</a> cred) , limitandu-se la blocuri cu tipul paragraph si un bloc cu tipul page_number