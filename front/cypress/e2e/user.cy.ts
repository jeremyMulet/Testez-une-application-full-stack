export function run(){
  describe('User flow', () => {
    it('Register successfully', () => {
      cy.visit('/register')

      cy.get('input[formControlName=firstName]').type("Toto")
      cy.get('input[formControlName=lastName]').type('Toto')
      cy.get('input[formControlName=email]').type('test@test.com')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type=submit]').click()

      cy.url().should('include', '/login')

    })

    it('should login', () => {

      cy.get('input[formControlName=email]').type("test@test.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

      cy.url().should('include', '/sessions')
    });

    it('should participate to first session', () => {
      cy.get('mat-card.item').first().contains('mat-icon', 'search').click()

      cy.url().should('include', '/sessions/detail')

      cy.get('button').contains('Participate').click()
      cy.get('button').contains('Do not participate').click()

    });

    it('should delete user account', () => {
      cy.get('span[routerLink=me]').click()

      cy.get('button').contains('Detail').click()
    });

    it('should display Not Found component', () => {
        cy.visit('/wrongroute')

        cy.url().should('include', '/404')
        cy.get('h1').should('contain', 'Page not found');
    });
  });
}
