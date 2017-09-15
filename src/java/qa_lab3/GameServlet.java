package qa_lab3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;

import qa_lab3.TicTacToeHelper.*;
/**
 *
 * @author venefica
 */
public class GameServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private TicTacToe game;
    private Player pl;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, BadRequestException {
        response.setContentType("text/html;charset=UTF-8");
        if (game == null || request.getParameter("new_game") != null) {
            Integer board_size, winning_len;
            try {
                board_size = Integer.parseInt(request.getParameter("board_size"));
                winning_len = Integer.parseInt(request.getParameter("winning_length"));
            } catch (Exception e) {
                throw new BadRequestException("invalid first player value");
            }
            
            String str_first_player = request.getParameter("first_player");
            Player first_player;
            if (str_first_player.equals("O")) {
                first_player = Player.O;
            } else if (str_first_player.equals("X")) {
                first_player = Player.X;
            } else {
                throw new BadRequestException("invalid first player value");
            }
            pl = first_player;
            
            try{
                game = new TicTacToe(board_size, winning_len, first_player);
            }
            catch (Exception e) {
                throw new BadRequestException("failed to create game "+e.getMessage());
            }     
            
        } 


        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>"); 
            out.println("<head>");
            out.println("<title>Tic tac toe</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<br/>");
            out.println("<form action=\"myservlet\" method=\"GET\">");
            out.println("<input type=\"submit\" name=\"move\" value=\"Make move\" id=\"makemove\">");
            out.println("<input type=\"submit\" name=\"move\" value=\"Best move\"/ id=\"bestmove\">");
            if (request.getParameterMap().containsKey("move")) {
                if (request.getParameter("move").equals("Best move")) {
                    Move m = game.getWinningMove(pl);
                    if (m != null) {
                        out.println("x "+m.getX().toString());
                        out.println("y "+m.getY().toString());
                    } else {
                        out.println("impossible to win");
                    }
                    int gameSize = game.getBoard().size();
                    out.println("<br/>");
                    for (int i = 0; i < gameSize; i++) {

                        for (int j = 0; j < gameSize; j++) {
                            if (game.getBoard().getCellPlayer(i, j) == TicTacToeHelper.Player.X) {
                                out.format(" X ");
                            } else if (game.getBoard().getCellPlayer(i, j) == TicTacToeHelper.Player.O) {
                                out.format(" O ");
                            } else{
                                    out.format("<input type=\"radio\" name=\"cell\" value=\"%d_%d\"/>", i, j);
                            }
                        }
                        out.println("<br/>");
                    }


                } else if (request.getParameter("move").equals("Make move")) {
                        Integer x, y;
                        try {
                            String cell = request.getParameter("cell");
                            x = Integer.parseInt(cell.split("_")[0]);
                            y = Integer.parseInt(cell.split("_")[1]);
                            Move m = new Move(x, y, pl);
                            game.makeMove(m);
                        } catch(Exception e) {
                            throw new BadRequestException("failed to make move"+e.getMessage());
                        }
                        pl = TicTacToeHelper.other(pl);

                        int gameSize = game.getBoard().size();
                        out.println("<br/>");
                        for (int i = 0; i < gameSize; i++) {

                            for (int j = 0; j < gameSize; j++) {
                                if (game.getBoard().getCellPlayer(i, j) == TicTacToeHelper.Player.X) {
                                    out.format(" X ");
                                } else if (game.getBoard().getCellPlayer(i, j) == TicTacToeHelper.Player.O) {
                                    out.format(" O ");
                                } else{
                                    out.format("<input type=\"radio\" name=\"cell\" value=\"%d_%d\"/>", i, j);
                                }
                            }
                            out.println("<br/>");
                        }
                    }
            } else {
                out.println("<input type=\"number\" name=\"x\"/>");
                out.println("<input type=\"number\" name=\"y\"/>");
                int gameSize = game.getBoard().size();
                out.println("<br/>");
                for (int i = 0; i < gameSize; i++) {
                    for (int j = 0; j < gameSize; j++) {
                        if (game.getBoard().getCellPlayer(i, j) == TicTacToeHelper.Player.X) {
                            out.format(" X ");
                        } else if (game.getBoard().getCellPlayer(i, j) == TicTacToeHelper.Player.O) {
                            out.format(" O ");
                        } else{
                            out.format("<input type=\"radio\" name=\"cell\" value=\"%d_%d\"/>", i, j);
                        }
                    }
                    out.println("<br/>");
                }
            }
            
            if (pl.equals(Player.O)) {
                out.println("O's move");
            } else {
                out.println("X's move");
            }
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet responsex
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}