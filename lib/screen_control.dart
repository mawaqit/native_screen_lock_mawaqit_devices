import 'package:flutter/services.dart';

class ScreenControl {
  static const MethodChannel _channel = MethodChannel('screen_control');

  /// Toggle box screen off
  static Future<bool> toggleBoxScreenOff() async {
    try {
      final bool result = await _channel.invokeMethod('toggleBoxScreenOff');
      return result;
    } catch (e) {
      print('Error toggling box screen off: $e');
      return false;
    }
  }

  /// Toggle box screen on
  static Future<bool> toggleBoxScreenOn() async {
    try {
      final bool result = await _channel.invokeMethod('toggleBoxScreenOn');
      return result;
    } catch (e) {
      print('Error toggling box screen on: $e');
      return false;
    }
  }

  /// Toggle tablet screen off
  static Future<bool> toggleTabletScreenOff() async {
    try {
      final bool result = await _channel.invokeMethod('toggleTabletScreenOff');
      return result;
    } catch (e) {
      print('Error toggling tablet screen off: $e');
      return false;
    }
  }

  /// Toggle tablet screen on
  static Future<bool> toggleTabletScreenOn() async {
    try {
      final bool result = await _channel.invokeMethod('toggleTabletScreenOn');
      return result;
    } catch (e) {
      print('Error toggling tablet screen on: $e');
      return false;
    }
  }
}
