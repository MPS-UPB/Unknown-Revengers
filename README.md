Unknown-Revengers
=================

T4 - Layout Analyze

Scopul acestui proiect este de a crea un executabil care să permită corecția erorilor în ceea ce privește gruparea caracterelor în rânduri și a rândurilor în blocuri de text, a paginării, și a conținutului text propriu-zis.

###Workflow-ul tipic este următorul:###

- Utilizatorul încarcă un <a href="develop/execs/input/input.xml">fișier XML</a> asupra căruia a fost executată analiza de layout sau fie incarca o imagine și selecteaza ce analizator de layout sa foloseasca din <a href="develop/execs/xml_schemas/mock_layout.xsd">cele disponibile</a>. Un analizator este defapt un task definit in xsd. 
- Dupa ce se alege analizatorul, se încarcă imaginea. Cred ca se afiseaza imaginea si pe deasupra ei pot fi ilustrate componentele imaginii document: litere, rânduri de litere și blocuri text; în funcție de selecția utilizatorului, pot fi vizualizate toate cele 3 niveluri, sau doar anumite categorii.
- Dacă asupra textului nu s-a executat analiza OCR (initial nu este facuta analiza OCR pentru niciun bloc), se poate cere analiza aceasta selectând analiza OCR, fie pe toată imaginea, fie doar pe anumite rânduri din imagine
- Rezultatele analizei OCR pot fi corectate selectând linia dorită și modificând textul atașat care trebuie să fie atașat într-o casetă text alăturată (ceva gen click pe block si apare un popup cu textul)
- Dacă niciunul din blocurile text nu este identificat ca fiind număr de pagină, se poate fie rula modulul de numerotare a paginii (inexistent momentan cred), fie să se impună ca unul din blocuri să reprezinte numărul de pagină. Pentru ca blocul de pagină conține rânduri (de fapt un singur rând la o detecție corectă), numărul poate fi detectat prin OCR sau introdus manual
- Pe lângă aceste acțiuni, trebuie să fie posibilă redimensionarea unirea/spargerea blocurilor text, unirea/spargerea liniilor de litere
- Rezultatul va fi un fișier XML care descrie layout-ul unei imaginii, conform schemei (ca <a href="develop/execs/input/input.xml">aici</a> cred) , limitându-se la blocuri cu tipul paragraph și un bloc cu tipul page_number