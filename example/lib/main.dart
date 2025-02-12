import 'package:flutter/material.dart';
import 'package:screen_control/screen_control.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _status = 'No action taken';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Screen Control Example'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('Status: $_status'),
              const SizedBox(height: 20),
              // Box Controls
              Text(
                'Box Controls',
                style: Theme.of(context).textTheme.headlineSmall,
              ),
              const SizedBox(height: 10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: () async {
                      final result = await ScreenControl.toggleBoxScreenOn();
                      setState(() {
                        _status = 'Box Screen On: $result';
                      });
                    },
                    child: const Text('Turn Box On'),
                  ),
                  const SizedBox(width: 10),
                  ElevatedButton(
                    onPressed: () async {
                      final result = await ScreenControl.toggleBoxScreenOff();
                      setState(() {
                        _status = 'Box Screen Off: $result';
                      });
                    },
                    child: const Text('Turn Box Off'),
                  ),
                ],
              ),
              const SizedBox(height: 20),
              // Tablet Controls
              Text(
                'Tablet Controls',
                style: Theme.of(context).textTheme.headlineSmall,
              ),
              const SizedBox(height: 10),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: () async {
                      final result = await ScreenControl.toggleTabletScreenOn();
                      setState(() {
                        _status = 'Tablet Screen On: $result';
                      });
                    },
                    child: const Text('Turn Tablet On'),
                  ),
                  const SizedBox(width: 10),
                  ElevatedButton(
                    onPressed: () async {
                      final result =
                          await ScreenControl.toggleTabletScreenOff();
                      setState(() {
                        _status = 'Tablet Screen Off: $result';
                      });
                    },
                    child: const Text('Turn Tablet Off'),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
