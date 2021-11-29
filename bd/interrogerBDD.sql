--question 3

SELECT vis_matricule, tra_role, jjmmaa
FROM Travailler
WHERE tra_role = "Délégué";


-- question 4

SELECT t.vis_matricule, t.tra_role, t.reg_code, t.jjmmaa
    FROM ( SELECT vis_matricule, MAX(jjmmaa) AS MaxDate
    FROM Travailler
    GROUP BY vis_matricule) derniere_date_for_each_visiteur
JOIN Travailler t ON derniere_date_for_each_visiteur.vis_matricule = t.vis_matricule
AND derniere_date_for_each_visiteur.MaxDate = t.jjmmaa
AND t.tra_role = "Délégué";


-- question 5

SELECT v.vis_matricule, v.vis_nom, v.vis_prenom
    FROM ( SELECT vis_matricule, MAX(jjmmaa) AS MaxDate
    FROM Travailler
    GROUP BY vis_matricule) derniere_date_for_each_visiteur
JOIN Travailler t ON derniere_date_for_each_visiteur.vis_matricule = t.vis_matricule
JOIN Visiteur v ON v.vis_matricule = t.vis_matricule
AND derniere_date_for_each_visiteur.MaxDate = t.jjmmaa
AND t.tra_role = "Délégué"
AND v.vis_mdp = "azerty"
AND v.vis_matricule = "c14";
