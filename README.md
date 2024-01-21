# Motor de Cautare pentru Documente
### Student(i)

## Descriere
Acest proiect dezvolta un Motor de Cautare pentru Documente care gestioneaza si recupereaza documente in mod eficient. Motorul foloseste puterea arborilor B pentru stocarea documentelor, arborilor Red-Black pentru gestionarea istoricului de cautare si algoritmului Knuth-Morris-Pratt pentru potrivirea eficienta a sirurilor de caractere. Aceasta combinatie asigura un acces rapid la date, capacitate de cautare si gestionare a istoricului, facandu-l o solutie cuprinzatoare pentru recuperarea si gestionarea documentelor.

## Obiective
Obiectivele principale ale proiectului sunt:

* Implementarea unui sistem robust de stocare a documentelor folosind arbori B, asigurand gestionarea si recuperarea eficienta a datelor.
* Utilizarea arborilor Red-Black pentru mentinerea si afisarea istoricului de cautare cu performanta ridicata.
* Integrarea algoritmului Knuth-Morris-Pratt pentru potrivirea precisa si eficienta a sirurilor de caractere in timpul cautarilor.

## Structuri de Date Utilizate

### Arbori B
Arborii B sunt utilizati pentru a stoca documente. Naturaletea lor echilibrata si factorul de ramificare ii fac ideali pentru operatiile eficiente de stocare si recuperare. Arborii B permit accesul rapid la documente si operatiuni eficiente de inserare si stergere, imbunatatind in ansamblu performanta motorului de cautare.

### Arbori Red-Black
Arborii Red-Black sunt utilizati pentru stocarea istoricului de cautare. Proprietatea lor de auto-echilibrare asigura ca istoricul cautarilor este mentinut intr-un mod sortat si poate fi accesat cu un timp minim de complexitate. Aceasta caracteristica accelereaza semnificativ recuperarea istoricului de cautare.

### Algoritmul Knuth-Morris-Pratt (KMP)
Algoritmul KMP este integrat pentru potrivirea sirurilor de caractere in documente. Furnizeaza o abordare mai eficienta pentru cautarea de substraturi (de exemplu, cuvinte cheie sau fraze) in continutul documentului. Algoritmul reduce numarul de comparatii, accelerand astfel procesul de cautare in motorul de cautare.

## Functionalitati/Exemple de Utilizare/Teste/Referinte

### Functionalitati
- **Inserarea Documentelor**: Utilizatorii pot adauga documente noi in sistem, care sunt stocate in arborele B.
- **Cautarea Documentelor**: Motorul permite utilizatorilor sa caute documente bazate pe cuvinte cheie sau fraze folosind algoritmul KMP.
- **Stergerea Documentelor**: Documentele pot fi eliminate din sistem, cu arborele B gestionand eficient stergerea.
- **Vizualizarea Istoricului de Cautare**: Utilizatorii pot vedea cautarile lor anterioare, recuperate rapid din arborele Red-Black.

### Exemple de Utilizare
- **Cautare Document**: Un utilizator cauta documente care contin expresia "invatare automata", iar motorul recupereaza rapid documente relevante folosind algoritmul KMP.
- **Vizualizare Istoric de Cautare**: Utilizatorul isi poate vizualiza cautarile recente, care sunt afisate eficient din arborele Red-Black.

### Teste
Testele unitare acopera fiecare componenta (arbori B, arbori Red-Black, algoritmul KMP), asigurand fiabilitatea si acuratetea sistemului.

### Referinte
- Cursuri Structuri de date avansate, Universitatea de Vest Timisoara.
- Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). Introducere in Algoritmi. MIT Press.
- Cod: https://github.com/Dutzz23/ProiectSDA
