package bot.discord.yeti.game.roulette;

import java.io.Serializable;

public class Roulette implements Serializable {



    int spin;
    int mutliplier;
    String bet;


    public Roulette(int spin, String bet) {
        this.spin = spin;
        this.bet = bet;
    }

    public void calcMulti() {
        try {
            int num = Integer.parseInt(bet);

            if (num == spin)
                mutliplier = 36;
            else {

                mutliplier = 0;

            }

        } catch (Exception e) {

            if (bet.equals("red")) {

                if (spin == 1 || spin == 3 || spin == 5 || spin == 7 || spin == 9 || spin == 12 || spin == 14 || spin == 16 || spin == 18 || spin == 19 || spin == 21 || spin == 23 || spin == 25 || spin == 27 || spin == 30 || spin == 32 || spin == 34 || spin == 36) {

                    mutliplier = 2;

                } else {

                    mutliplier = 0;

                }


            } else if (bet.equals("black")) {

                if (spin == 2 || spin == 4 || spin == 6 || spin == 8 || spin == 10 || spin == 11 || spin == 13 || spin == 15 || spin == 17 || spin == 20 || spin == 22 || spin == 24 || spin == 26 || spin == 28 || spin == 29 || spin == 31 || spin == 33 || spin == 35) {

                    mutliplier = 2;

                } else {

                    mutliplier = 0;

                }


            } else if (bet.equals("green")) {

                if (spin == 0) {
                    mutliplier = 36;
                } else {

                    mutliplier = 0;


                }
            } else if (bet.equals("even")) {

                if (spin % 2 == 0) {
                    mutliplier = 2;
                } else {

                    mutliplier = 0;

                }

            } else if (bet.equals("odd")) {

                if (spin % 2 == 1) {
                    mutliplier = 2;
                } else {

                    mutliplier = 0;

                }

            }
        }




        }
        public int getMultiplier () {
        return mutliplier;
    }
    }

