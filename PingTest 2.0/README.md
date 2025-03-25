# 📡 PingApp-Java-Fixed

Ce projet est une **version corrigée, refactorée et améliorée** d'une petite application Java nommé **PingTest 2.0** trouvée sur GitHub, qui mesurait le ping vers un serveur donné et l’affichait via une surcouche graphique simple.

## 🙏 Crédit original

Projet initialement développé par [Necroliner](https://github.com/Necroliner) : [lien vers le repo original](https://github.com/Necroliner/PingTest.git).
Nom original du fichier : `PingTest 2.0` (Java)
Code publié initialement sans licence – utilisation dans un but pédagogique et d’apprentissage.

## 🎯 Objectif du projet

L’objectif était initialement de créer une interface graphique minimaliste affichant en direct le **ping vers une IP spécifique**.

Cependant, le code original souffrait de plusieurs problèmes :
- UI figée et peu interactive
- Gestion des threads hasardeuse (Timer vs Swing)
- Pas de contrôle utilisateur (impossible de changer l’IP)
- Code peu structuré / difficile à maintenir

## 🔧 Améliorations apportées

| Élément                  | Avant                                           | Après                                                        |
|--------------------------|--------------------------------------------------|---------------------------------------------------------------|
| **Threading**            | `TimerTask` bloquant                            | `ScheduledExecutorService` pour un threading propre           |
| **Initialisation UI**    | Interface auto-lancée sans contrôle             | Lancement explicite, meilleure séparation des responsabilités |
| **Contrôle IP**          | IP fixe codée en dur                            | Champ utilisateur pour modifier dynamiquement l’adresse       |
| **Boutons de contrôle**  | Aucun                                           | Ajout des boutons PING / STOP / QUITTER                       |
| **Couleurs du ping**     | Codées pour secondes (!), peu lisibles          | Conversion correcte + codes couleur bien calibrés             |
| **Code structuré**       | 1 seul gros bloc, pas de gestion d’erreurs      | Séparation claire, gestion propre des exceptions              |
| **Shutdown propre**      | Aucun hook / fuite mémoire potentielle          | Hook d’arrêt + reset du scheduler                             |

## 📷 Aperçu

 A completer (Captures d'écran Terminal + App Swing) 

---

## 🚀 Lancer le projet

A completer (spécifcations configs versions)

---

## 🧠 Ce que j'ai appris

- Bonnes pratiques en gestion de threads Java
- Intégration de composants Swing réactifs
- UI minimaliste mais interactive
- Nettoyage et refonte de code existant : refactor responsable

---
