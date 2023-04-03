Feature: Registration and Product Buy

  @Evershop_Register_Account
  Scenario Outline:New user registration account
      Given User open the Evershop Web "for registration"
      And User clicks on Login icon
      And User clicks on Account Creation
      And User completes information for registration "<UserName>","<Email>","<Password>"
      Then User verify the correct creation of the account with "<Email>" validation
      And User Evidence Create Account "<UserName>","<Email>"
    Examples:
      |UserName  |Email          |Password   |
      |Hans      |Hans12@Test.com|Richi123!  |

  @Evershop_Buy_Shoes
  Scenario Outline: User buys different products
      Given User open the Evershop Web "for shopping"
      And User clicks on Login icon
      And User log with credentials using "<Email>","<Password>"
      Then User verify the correct login of the account with "<Email>" validation
      And User select Category Product "Men"
      When User select Shoes to buy by "<ProductsDetail_Men>"
      And User select Category Product "Kids"
      When User select Shoes to buy by "<ProductsDetail_Kids>"
      And User select Category Product "Women"
      When User select Shoes to buy by "<ProductsDetail_Women>"
      And User clicks on miniCart and Checkout
      And User complete the information for Payment
      And User choose the payment method and place order
      Then User verify the Buy Order
      And User Create the buy report "<UserName>","<Email>","<ProductsDetail_Men>","<ProductsDetail_Kids>","<ProductsDetail_Women>"

    Examples:                                 #ShoesModel-Qty-Size-Colour
      |UserName  |Email          |Password   |ProductsDetail_Men           |ProductsDetail_Kids             |ProductsDetail_Women     |
      |Hans      |Hans6@Test.com|Richi123!  |Nike varsity-2-M-White       |Nike court vision low-2-X-Black |Strutter shoes-5-XL-Grey |
