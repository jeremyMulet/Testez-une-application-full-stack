export function run(){
  describe('register spec', () => {
    it('Register successfully', () => {
      cy.visit('/register')

      cy.intercept('POST', '/api/auth/register', {
        body: {"message":"User registered successfully!"},
      })

      cy.get('input[formControlName=firstName]').type("Toto")
      cy.get('input[formControlName=lastName]').type('Toto')
      cy.get('input[formControlName=email]').type('test@test.com')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type=submit]').click()

      cy.url().should('include', '/login')

    })

    it('Register failed email already taken', () => {
      cy.visit('/register')

      cy.get('input[formControlName=firstName]').type("Toto")
      cy.get('input[formControlName=lastName]').type('Toto')
      cy.get('input[formControlName=email]').type('yoga@studio.com')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type=submit]').click()

      cy.contains('An error occurred').should('be.visible')
      cy.url().should('include', '/register')
    })

    it('Register firstName empty', () => {
      cy.visit('/register')

      cy.get('input[formControlName=lastName]').type('Toto')
      cy.get('input[formControlName=email]').type('toto@test.com')
      cy.get('input[formControlName=password]').type('test!1234')

      cy.get('button[type=submit]').should('be.disabled')
      cy.url().should('include', '/register')
    })

    it('Register lastName empty', () => {
      cy.visit('/register')

      cy.get('input[formControlName=firstName]').type('Toto')
      cy.get('input[formControlName=email]').type('toto@test.com')
      cy.get('input[formControlName=password]').type('test!1234')

      cy.get('button[type=submit]').should('be.disabled')
      cy.url().should('include', '/register')
    })

    it('Register email empty', () => {
      cy.visit('/register')

      cy.get('input[formControlName=firstName]').type('Toto')
      cy.get('input[formControlName=lastName]').type('Toto')
      cy.get('input[formControlName=password]').type('test!1234')

      cy.get('button[type=submit]').should('be.disabled')
      cy.url().should('include', '/register')
    })

    it('Register password empty', () => {
      cy.visit('/register')

      cy.get('input[formControlName=firstName]').type('Toto')
      cy.get('input[formControlName=lastName]').type('Toto')
      cy.get('input[formControlName=email]').type('toto@test.com')

      cy.get('button[type=submit]').should('be.disabled')
      cy.url().should('include', '/register')
    })
  })
}
