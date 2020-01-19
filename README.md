# cordova-plugin-plc4x

Read from PLCs

```javascript
PLC4X.connect(this.url, async (res) => {
      console.log(res);
    }, async (res) => {
      console.log(res);
    });

let  values = [
    { name: 'motor-current', fieldQuery: '%DB444.DBD8:REAL' },
    { name: 'position', fieldQuery: '%DB444.DBD0:REAL' },
    { name: 'rand_val', fieldQuery: '%DB444.DBD4:REAL' }
  ];

 PLC4X.read(this.values, (res) => {
        console.log(res);
      }, async (err) => {
        console.log(err);
      });
```