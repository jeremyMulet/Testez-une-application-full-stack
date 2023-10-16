export function run(){
  describe('Login spec', () => {
    it('Login successfull', () => {
      cy.visit('/login')

      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

      cy.url().should('include', '/sessions')
    });

    it('Login failed wrong password', () => {
      cy.visit('/login');

      cy.get('input[formControlName=email]').type("yoga@studio.com");
      cy.get('input[formControlName=password]').type("test!123");
      cy.get('button[type=submit]').click();

      cy.url().should('include', '/login');
      cy.contains('An error occurred').should('be.visible');
    });

    it('Login failed no email', () => {
      cy.visit('/login');

      cy.get('input[formControlName=password]').type("test!123");

      cy.get('button[type=submit]').should('be.disabled');
      cy.url().should('include', '/login');
    });

    it('Login failed no password', () => {
      cy.visit('/login');

      cy.get('input[formControlName=email]').type("yoga@studio.com");

      cy.get('button[type=submit]').should('be.disabled');
      cy.url().should('include', '/login');
    });
  });
}
