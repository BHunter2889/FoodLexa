# Hack the Lou SmartLabel APIs

## Request

Header for all requests:
```
x-api-key: 9qQUNqKiSo6HNuo1C41EBWD3RkLwjsN1PIKmbPKb
```

URL parent for all rest endpoints:
```
https://dev-enterprise-sl-api.labelinsight.com/api/v4/20ff77e9-75af-410d-98ff-bef2febb0df7
```
The following endpoints assume the above is prepended

### Pull product by UPC:
```sh
/data/upc/:upc
# /data/upc/070920475578
```

### Search for products by title
```sh
/data/search?title=:title
# /data/search?title=Dark%20Chocolate%20Hot%20Cocoa
```

The search endpoint will return products with titles that are a fuzzy match to the request parameter.

#### Example 1: "Hot Cocoa"

"Chocolatier Series, Double Chocolate Gourmet Hot Cocoa Mix"
"Chocolatier Series, Dark Chocolate Gourmet Hot Cocoa Mix"

#### Example 2: “Dark Chocolate Hot Cocoa”

"Chocolatier Series, Dark Chocolate Gourmet Hot Cocoa Mix"

## Response

Both APIs return `JSON` with the following structure:
```js
{
  content: [
    {
      id: Number,
      upc: String,
      productType: String,
      manufacturer: String,
      distributor: String,
      brand: String,
      subBrand: Object,
      title: String,
      description: String,
      flavour: String,
      productSize: String,
      productSize2: String,
      dateCollected: String,
      timestamp: String,
      lastIndexed: Object,
      landingPageDisclaimer: Object,
      warnings: Object,
      rawIngredients: String,
      marketingImage: Object,
      usdaCertification: Object,
      nutritionSection: Object,
      ingredientSection: Object,
      allergenSection: Object,
      otherSection: Object,
      advisorySection: Object,
      usageSection: Object,
      companyBrandSection: Object,
      labelType: Object,
      uniqueIdentifiers: Object
    }
  ]
}
```

