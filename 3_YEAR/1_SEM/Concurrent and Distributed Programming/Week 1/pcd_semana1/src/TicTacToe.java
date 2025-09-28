import javax.swing.*;

public class TicTacToe extends Grid
{
    private boolean Turn;

    public TicTacToe( String wndTitle, int widthPx )
    {
        super( wndTitle, 3, 3, widthPx );
        this.Turn = false;
    }

    @Override
    public JLabel changeLabel( JLabel label )
    {
        if(!label.getText().isBlank())
            return label;

        if(Turn)
        {
            label.setText("O");
            Turn = false;
        }
        else {
            label.setText("X");
            Turn = true;
        }
        return label;
    }

    public static void main( String[] args )
    {
        TicTacToe game = new TicTacToe( "TicTacToe", 75 );
    }
}
