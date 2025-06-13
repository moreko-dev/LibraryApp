package dev.moreko.librarymanager.view;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.moreko.librarymanager.comp.SidebarItem;
import dev.moreko.librarymanager.theme.Icons;
import dev.moreko.librarymanager.theme.ThemeManager;
import dev.moreko.librarymanager.utils.GraphicUtils;
import net.miginfocom.swing.MigLayout;

// This view contains the main frame...
public class MainView extends JFrame {
    private JPanel container = new JPanel(new MigLayout("fill, inset 0", "[200!][grow]", "[][grow]"));
    private JPanel topPanel = new JPanel();
    private JPanel sidePanel = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JLabel addressLabel = new JLabel("Library App > Dashboard");
    private JButton saveButton = new JButton("Save", Icons.SAVE_BUTTON);
    private SidebarItem dashboardButton = new SidebarItem("Dashboard", Icons.DASHBOARD_ICON);
    private SidebarItem memberButton = new SidebarItem("Member", Icons.MEMBER_ICON);
    private SidebarItem booksButton = new SidebarItem("Books", Icons.BOOK_ICON);
    private SidebarItem borrowsButton = new SidebarItem("Borrows", Icons.BORROW_ICON);
    private SidebarItem helpButton = new SidebarItem("Help", Icons.HELP_ICON);
    private CardLayout mainPanelLayout = new CardLayout();
    private DashboardView dashboardView = new DashboardView();
    private BooksView booksView = new BooksView();
    private MembersView membersView = new MembersView();
    private BorrowsView borrowsView = new BorrowsView();
    private HelpView helpView = new HelpView();
    private MemberFormView memberFormView = new MemberFormView();
    private BorrowFormView borrowFormView = new BorrowFormView();
    private BookFormView bookFormView = new BookFormView();

    public MainView(String title) {
        super(title);
        GraphicUtils.setFrameMaximized(this);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
        topPanel.setBackground(ThemeManager.getTopPanelBackground());
        sidePanel.setBackground(ThemeManager.getSidePanelBackground());
        mainPanel.setBackground(ThemeManager.getMainPanelBackground());
    
        topPanel.setLayout(new MigLayout("inset 10", "[][]", "[]"));
        topPanel.add(addressLabel, "push");
        topPanel.add(saveButton, "");
    
        sidePanel.setLayout(new MigLayout("inset 10 5", "[grow]", ""));
        sidePanel.add(dashboardButton, "wrap");
        sidePanel.add(booksButton, "wrap");
        sidePanel.add(memberButton, "wrap");
        sidePanel.add(borrowsButton, "wrap");
        sidePanel.add(helpButton, "wrap");
    
        mainPanel.setLayout(mainPanelLayout);
        mainPanel.add(dashboardView, "dashboard");
        mainPanel.add(booksView, "books");
        mainPanel.add(membersView, "members");
        mainPanel.add(borrowsView, "borrows");
        mainPanel.add(helpView, "help");
        mainPanel.add(memberFormView, "member_form");
        mainPanel.add(bookFormView, "book_form");
        mainPanel.add(borrowFormView, "borrow_form");
        mainPanelLayout.show(mainPanel, "dashboard");
    
        container.add(topPanel, "span, grow, wrap");
        container.add(sidePanel, "grow");
        container.add(mainPanel, "grow");
    
        this.add(container);
    }

    public JButton getSaveButton() { return saveButton; }
    public SidebarItem getDashboardButton() { return dashboardButton; }
    public SidebarItem getMembersButton() { return memberButton; }
    public SidebarItem getBooksButton() { return booksButton; }
    public SidebarItem getBorrowsButton() { return borrowsButton; }
    public SidebarItem getHelpButton() { return helpButton; }
    public CardLayout getMainLayout() { return mainPanelLayout; }
    public JLabel getAddressLabel() { return addressLabel; }
    public JPanel getMainPanel() { return mainPanel; }
    public DashboardView getDashboardView() { return dashboardView; }
    public BooksView getBooksView() { return booksView; }
    public MembersView getMembersView() { return membersView; }
    public BorrowsView getBorrowsView() { return borrowsView; }
    public HelpView getHelpView() { return helpView; }
    public MemberFormView getMemberFormView() { return memberFormView; }
    public BookFormView getBookFormView() { return bookFormView; }
    public BorrowFormView getBorrowFormView() { return borrowFormView; }
}
