export function run(){
  describe('Admin flow', () => {
    it('Login successfull', () => {
      cy.visit('/login')

      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

      cy.url().should('include', '/sessions')
    });

    it('should display session detail and go back', () => {
      cy.get('mat-card.item').first().contains('mat-icon', 'search').click()

      cy.url().should('include', '/sessions/detail')

      cy.get('button[mat-icon-button]').contains('mat-icon', 'arrow_back').click();

      cy.url().should('include', '/sessions')
    });

    it('should display create form', () => {
      cy.get('button[routerLink=create]').click()

      cy.url().should('include', '/create')
    });

    it('should create session', () => {

      cy.get('input[formControlName=name]').type('Session test')
      cy.get('input[formControlName=date]').type('2024-01-01')
      cy.get('mat-select[formControlName=teacher_id]').click()
      cy.get('mat-option').first().click();
      cy.get('textarea[formControlName=description]').type('Description Test')

      cy.get('button[type=submit]').click()

      cy.get('span[routerLink=sessions]').click()

      cy.get('mat-card.item').last().get('mat-card-title').should('contain.text', 'Session test')
      cy.url().should('include', '/sessions')
    });

    it('should update session', () => {
      cy.get('mat-card.item').last().contains('mat-icon', 'edit').click()

      cy.url().should('include', '/sessions/update')

      cy.get('input[formControlName=name]').type('Session test updated')
      cy.get('input[formControlName=date]').type('2024-01-02')
      cy.get('mat-select[formControlName=teacher_id]').click()
      cy.get('mat-option').last().click();
      cy.get('textarea[formControlName=description]').type('Description Test updated')
      cy.get('button[type=submit]').click();

      cy.get('mat-card.item').last().get('mat-card-title').should('contain.text', 'Session test updated')
    });

    it('should delete session', () => {
      cy.get('mat-card.item').last().contains('mat-icon', 'search').click()

      cy.get('button[color=warn]').click()

      cy.get('mat-card.item').last().get('mat-card-title').should('not.contain.text', 'Session test updated');


    });

    it('should display user information', () => {
      cy.get('span[routerLink=me]').click()

      cy.url().should('include', '/me')

      cy.get('button[mat-icon-button]').contains('mat-icon', 'arrow_back').click()
    });

    it('should logout', () => {
      cy.contains('span', 'Logout').click()

      cy.url().should('eq', 'http://localhost:4200/')
    });

  });
}
