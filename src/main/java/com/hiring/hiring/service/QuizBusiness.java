package com.hiring.hiring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizBusiness implements IServiceQuiz {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    QuizRepository quizrepository;
    @Override
    public QuizDTO getquizbyid(String id) {
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);

        return mapper.map(quiz, QuizDTO.class);

    }

    @Override
    public float calculatescoreqcu(String id, QuizDTO quizdto,Long idUser) {
        float s = 0;
        float nb = 0;
        float score=0;
        //quizdto ali jeni mil apprenant
        //quiz ali 5al9ou il formateur

        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);



        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));
        for (int i = 0; i <= quizdto.getQuestionsList().size() - 1; i++) {

            nb+=quiz.getQuestionsList().get(i).getPoints();

            if (quizdto.getQuestionsList().get(i).getCorrectanswer().get(0).equals(quiz.getQuestionsList().get(i).getCorrectanswer().get(0))) {

                s += quiz.getQuestionsList().get(i).getPoints();//les points mta il quiz ali amlou il formateur

            }


        }
        if(nb!=0)
        {score+=(s/nb)*100;}
        Corrections c=new Corrections(idUser,score);
        quiz.getCorrectionsList().add(c);
        quiz.setCorrectionsList(quiz.getCorrectionsList());
        quizrepository.save(quiz);

        return score;
    }





    @Override
    public float getopenquestionsallornothing(String id, QuizDTO quizdto,Long idUser){
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);
        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));

        float score=0;


        float nbanswers = 0;

        float s = 0;


        for (int i = 0; i <= quiz.getQuestionsList().size() - 1; i++) {


            for(int b=0;b<=quiz.getQuestionsList().get(i).getCorrectanswer().size()-1;b++) {

                if(quizdto.getQuestionsList().get(i).getCorrectanswer().get(0).contains(quiz.getQuestionsList().get(i).getCorrectanswer().get(b)))

                {
                    nbanswers++;}
                if(nbanswers==quiz.getQuestionsList().get(i).getCorrectanswer().size())
                {
                    s+=quiz.getQuestionsList().get(i).getPoints();
                }
            }


        }
        score+=(s*100)/quiz.getQuestionsList().stream().mapToDouble(Questions::getPoints).sum();
        return score;}
    @Override
    public float getopenquestionspartialcredit(String id, QuizDTO quizdto,Long idUser){
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);
        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));

        float score=0;

        float valeurreponse = 0;
        float nbanswers = 0;

        float s = 0;

        for (int i = 0; i <= quiz.getQuestionsList().size() - 1; i++) {


            valeurreponse += quiz.getQuestionsList().get(i).getPoints() /  quiz.getQuestionsList().get(i).getCorrectanswer().size();


            for (int b = 0; b <= quiz.getQuestionsList().get(i).getCorrectanswer().size() - 1; b++) {

                if (quizdto.getQuestionsList().get(i).getCorrectanswer().get(0).contains(quiz.getQuestionsList().get(i).getCorrectanswer().get(b))) {

                    nbanswers++;
                }
            }
            s += nbanswers * valeurreponse;

            nbanswers = 0;
            valeurreponse = 0;
        }
        score+=(s*100)/quiz.getQuestionsList().stream().mapToDouble(Questions::getPoints).sum();
        Corrections c=new Corrections(idUser,score);
        quiz.getCorrectionsList().add(c);
        quiz.setCorrectionsList(quiz.getCorrectionsList());
        quizrepository.save(quiz);
        return score;}
    @Override
    public float getopenquestionsguessingpenalty(String id, QuizDTO quizdto,Long idUser){
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);
        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));

        float score=0;

        float valeurreponse = 0;
        float nbanswers = 0;
        float nbfausse= 0;
        float valeurreponsefausse = 0;
        float valeurreponsecorrecte = 0;
        float s = 0;

        for (int i = 0; i <= quiz.getQuestionsList().size() - 1; i++) {

            valeurreponse +=  quiz.getQuestionsList().get(i).getPoints() / quiz.getQuestionsList().get(i).getCorrectanswer().size();


            for(int b=0;b<=quiz.getQuestionsList().get(i).getCorrectanswer().size()-1;b++) {

                if(quizdto.getQuestionsList().get(i).getCorrectanswer().get(0).contains(quiz.getQuestionsList().get(i).getCorrectanswer().get(b)))

                {
                    nbanswers++;

                }
                if(nbanswers<quiz.getQuestionsList().get(i).getCorrectanswer().size() && nbanswers>=1 )
                {nbfausse=quiz.getQuestionsList().get(i).getCorrectanswer().size()-nbanswers;
                }

            }

            valeurreponsecorrecte+=(valeurreponse*nbanswers);
            valeurreponsefausse+=(valeurreponse*nbfausse);
            if(nbanswers==quiz.getQuestionsList().get(i).getCorrectanswer().size())
            {s+=valeurreponsecorrecte;}
            else{s+=(valeurreponsecorrecte-valeurreponsefausse);}

            if(s<0){s*=-1;}


            nbanswers=0;
            nbfausse=0;
            valeurreponse=0;
            valeurreponsefausse=0;
            valeurreponsecorrecte=0;}
        score+=(s*100)/quiz.getQuestionsList().stream().mapToDouble(Questions::getPoints).sum();
        Corrections c=new Corrections(idUser,score);
        quiz.getCorrectionsList().add(c);
        quiz.setCorrectionsList(quiz.getCorrectionsList());
        quizrepository.save(quiz);
        return score;}

    @Override
    public float getqcmallornothing(String id, QuizDTO quizdto,Long idUser) {
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);

        int nb=0;
        int nbcorrect = 0;

        int verifnbreponsecorrect = 0;

        float s = 0;

        //all nor nothing
        List<String> quizsorted = null;
        List<String> quizdtosorted = null;
        //all or nothing
        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));
        nb+=quiz.getQuestionsList().stream().mapToDouble(Questions::getPoints).sum();



        for (int i = 0; i <= quizdto.getQuestionsList().size() - 1; i++) {

            quizsorted = quiz.getQuestionsList().get(i).getCorrectanswer().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            quizdtosorted = quizdto.getQuestionsList().get(i).getCorrectanswer().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());

            if (quizdto.getQuestionsList().get(i).getCorrectanswer().size() == quiz.getQuestionsList().get(i).getCorrectanswer().size()) {
                nbcorrect = quiz.getQuestionsList().get(i).getCorrectanswer().size();
                for (int j = 0; j <= nbcorrect - 1; j++) {
                    if (quizsorted.get(j).equals(quizdtosorted.get(j))) {
                        verifnbreponsecorrect++;
                        if (verifnbreponsecorrect == nbcorrect) {
                            s += quiz.getQuestionsList().get(i).getPoints() * 100;

                        }


                    }

                }
                verifnbreponsecorrect = 0;

            }

        }
        Corrections c=new Corrections(idUser,s/nb);
        quiz.getCorrectionsList().add(c);
        quiz.setCorrectionsList(quiz.getCorrectionsList());
        quizrepository.save(quiz);
        return s/nb;
    }

    @Override
    public float getqcmpartialcredit(String id, QuizDTO quizdto,Long idUser) {
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);

        int nb=0;
        int nbcorrect = 0;

        int verifnbreponsecorrect = 0;

        float s = 0;
        double valeurreponse = 0;

        //all nor nothing
        List<String> quizsorted = null;
        List<String> quizdtosorted = null;
        //all or nothing
        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));
        nb+=quiz.getQuestionsList().stream().mapToDouble(Questions::getPoints).sum();
        for (int i = 0; i <= quizdto.getQuestionsList().size() - 1; i++) {

            quizsorted = quiz.getQuestionsList().get(i).getCorrectanswer().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            quizdtosorted = quizdto.getQuestionsList().get(i).getCorrectanswer().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            nbcorrect = quiz.getQuestionsList().get(i).getCorrectanswer().size();
            valeurreponse += quiz.getQuestionsList().get(i).getPoints() / nbcorrect; //valeur d'une reponse correcte % au point de chaque question


            if (quiz.getQuestionsList().get(i).getCorrectanswer().size() == quizdto.getQuestionsList().get(i).getCorrectanswer().size())
//nafs size
            {

                for (int j = 0; j <= nbcorrect - 1; j++) {
                    if (quizsorted.get(j).equals(quizdtosorted.get(j))) {
                        verifnbreponsecorrect++;

                    }



                }
            }



            else if (quizdto.getQuestionsList().get(i).getCorrectanswer().size() == 1) {
                for (int b = 0; b <= quiz.getQuestionsList().get(i).getCorrectanswer().size() - 1; b++) {
                    if (quiz.getQuestionsList().get(i).getCorrectanswer().get(b).equals(quizdto.getQuestionsList().get(i).getCorrectanswer().get(0))) {
                        verifnbreponsecorrect = 1;
                    }

                }

            } else {
                for (int l = 0; l <= quizdto.getQuestionsList().get(i).getCorrectanswer().size() - 1; l++) {
                    for (int b = 0; b <= quiz.getQuestionsList().get(i).getCorrectanswer().size() - 1; b++) {
                        if (quiz.getQuestionsList().get(i).getCorrectanswer().get(b).equals(quizdto.getQuestionsList().get(i).getCorrectanswer().get(l))) {
                            verifnbreponsecorrect++;
                        }
                    }
                }
            }

            valeurreponse *= verifnbreponsecorrect;
            s += valeurreponse * 100;
            valeurreponse = 0;
            verifnbreponsecorrect = 0;

        }
        Corrections c=new Corrections(idUser,s/nb);
        quiz.getCorrectionsList().add(c);
        quiz.setCorrectionsList(quiz.getCorrectionsList());
        quizrepository.save(quiz);
        return s/nb ;}

    @Override
    public float getqcmguessingpenalty(String id, QuizDTO quizdto,Long idUser) {
        Quiz quiz = quizrepository.findById(id).orElseGet(Quiz::new);

        int nb=0;
        int nbcorrect = 0;

        int verifnbreponsecorrect = 0;
        int verifnbreponsefausse = 0;
        float s = 0;
        double valeurreponse = 0;
        double valeurreponsefausse = 0;
        //all nor nothing
        List<String> quizsorted = null;
        List<String> quizdtosorted = null;
        //all or nothing
        quiz.getQuestionsList().sort(Comparator.comparing(Questions::getQuestion));
        nb+=quiz.getQuestionsList().stream().mapToDouble(Questions::getPoints).sum();
        for (int i = 0; i <= quizdto.getQuestionsList().size() - 1; i++) {
            quizsorted = quiz.getQuestionsList().get(i).getCorrectanswer().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            quizdtosorted = quizdto.getQuestionsList().get(i).getCorrectanswer().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            nbcorrect = quiz.getQuestionsList().get(i).getCorrectanswer().size();

            valeurreponse += quiz.getQuestionsList().get(i).getPoints() / nbcorrect; //valeur d'une reponse correcte % au point de chaque question
            valeurreponsefausse += quiz.getQuestionsList().get(i).getPoints() /  nbcorrect; //valeur d'une reponse correcte % au point de chaque question
            if (quiz.getQuestionsList().get(i).getCorrectanswer().size() == quizdto.getQuestionsList().get(i).getCorrectanswer().size())
//nafs size
            {
                for (int j = 0; j <= nbcorrect - 1; j++) {
                    //si le nb de reponse forunis=nb de reponses supposés correcte
                    if (quizdtosorted.get(j).equals(quizsorted.get(j))) {
                        verifnbreponsecorrect++;

                    }
                    if (!quizdtosorted.get(j).equals(quizsorted.get(j))) {
                        verifnbreponsefausse++;


                    }


                }


            }
            //user da5el ka3ba bark
            else if (quizdto.getQuestionsList().get(i).getCorrectanswer().size() == 1) {
                for (int b = 0; b <= quiz.getQuestionsList().get(i).getCorrectanswer().size() - 1; b++) {
                    if (quiz.getQuestionsList().get(i).getCorrectanswer().get(b).equals(quizdto.getQuestionsList().get(i).getCorrectanswer().get(0))) {
                        verifnbreponsecorrect = 1;
                    }
                }
            }
            //user da5el akther mil matloub
            else {
                for (int l = 0; l <= quizdto.getQuestionsList().get(i).getCorrectanswer().size() - 1; l++) {
                    for (int b = 0; b <= quiz.getQuestionsList().get(i).getCorrectanswer().size() - 1; b++) {
                        if (quiz.getQuestionsList().get(i).getCorrectanswer().get(b).equals(quizdto.getQuestionsList().get(i).getCorrectanswer().get(l))) {
                            verifnbreponsecorrect++;
                        }
                    }
                }
                verifnbreponsefausse += quizdto.getQuestionsList().get(i).getCorrectanswer().size() - verifnbreponsecorrect;
                if (verifnbreponsefausse > quiz.getQuestionsList().get(i).getCorrectanswer().size()) {
                    valeurreponsefausse = 0;
                    valeurreponse = 0;
                }




            }

            if (verifnbreponsefausse > verifnbreponsecorrect) {
                s += 0;
            } else {
                valeurreponse *= verifnbreponsecorrect;
                valeurreponsefausse *= verifnbreponsefausse;

                s += (valeurreponse - valeurreponsefausse) * 100;
            }
            valeurreponse = 0;
            valeurreponsefausse = 0;
            verifnbreponsefausse = 0;
            verifnbreponsecorrect = 0;

        }
        Corrections c=new Corrections(idUser,s/nb);
        quiz.getCorrectionsList().add(c);
        quiz.setCorrectionsList(quiz.getCorrectionsList());
        quizrepository.save(quiz);
        return s/nb; }
}

