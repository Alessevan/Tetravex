## Tetravex Solver

### Description

Tetravex Solver est un logiciel en Java capable de résoudre une partie de Tetravex.
Il a été fait à partir des spécifications [disponibles ici](https://gitlab.com/EclipseOnFire/itsalexousd-discord-test/-/wikis/home).

### Comment lancer le programme ?

Pour lancer le programme il faut disposer d'un fichier contenant les informations en binaire (cf spécifications)
nommé `tosolve.bin` par exemple. Puis exécuter le fichier java ayant la version X avec la commande
`cat tosolve.bin | java -jar Tetravex-X.jar`. Les informations seront renvoyées en binaire dans le terminal.
Pour renvoyer le résultat dans un fichier, il faut rajouter `> solved.bin` à la suite de la commande afin d'avoir le résultat
dans le fichier `solved.bin`. La commande complète est donc `cat tosolve.bin | java -jar Tetravex-X.jar > solved.bin`. 
