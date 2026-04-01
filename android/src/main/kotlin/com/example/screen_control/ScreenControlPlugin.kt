package com.example.screen_control

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import android.os.AsyncTask
import android.util.Log

class ScreenControlPlugin: FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "screen_control")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "toggleBoxScreenOff" -> toggleBoxScreenOff(result)
            "toggleBoxScreenOn" -> toggleBoxScreenOn(result)
            "toggleTabletScreenOff" -> toggleTabletScreenOff(result)
            "toggleTabletScreenOn" -> toggleTabletScreenOn(result)
            else -> result.notImplemented()
        }
    }

    private fun toggleBoxScreenOff(result: Result) {
        AsyncTask.execute {
            try {
                val commands = listOf(
                    "mount -o rw,remount /",
                    "cd /sys/class/hdmi/hdmi/attr",
                    "echo 0 > phy_power",
                    "sleep 1",
                    "service call hdmi_control 16 i32 1"
                )
                executeCommand(commands, result)
            } catch (e: Exception) {
                handleCommandException(e, result)
            }
        }
    }

    private fun toggleTabletScreenOff(result: Result) {
        AsyncTask.execute {
            try {
                val commands = listOf(
                    "echo 0 > /sys/class/backlight/backlight/brightness"
                )
                executeCommand(commands, result)
            } catch (e: Exception) {
                handleCommandException(e, result)
            }
        }
    }

    private fun toggleTabletScreenOn(result: Result) {
        AsyncTask.execute {
            try {
                val commands = listOf(
                    "echo 110 > /sys/class/backlight/backlight/brightness"
                )
                executeCommand(commands, result)
            } catch (e: Exception) {
                handleCommandException(e, result)
            }
        }
    }

    private fun toggleBoxScreenOn(result: Result) {
        AsyncTask.execute {
            try {
                val commands = listOf(
                    "mount -o rw,remount /",
                    "cd /sys/class/hdmi/hdmi/attr",
                    "echo 0 > phy_power",
                    "sleep 2",
                    "echo 1 > phy_power"
                )
                executeCommand(commands, result)
            } catch (e: Exception) {
                handleCommandException(e, result)
            }
        }
    }

    private fun executeCommand(commands: List<String>, result: Result) {
        try {
            Log.d("SU_COMMAND", "Executing commands: ${commands.joinToString(separator = " && ")}")

            val suProcess = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(suProcess.outputStream)

            val command = commands.joinToString(separator = " && ") + "\n"
            Log.d("SU_COMMAND", "Writing command to DataOutputStream: $command")
            os.writeBytes(command)
            os.flush()
            os.close()

            val output = BufferedReader(InputStreamReader(suProcess.inputStream)).readText()
            val error = BufferedReader(InputStreamReader(suProcess.errorStream)).readText()

            Log.i("SU_COMMAND", "Command output: $output")
            Log.e("SU_COMMAND", "Command error: $error")

            val exitCode = suProcess.waitFor()
            Log.d("SU_COMMAND", "Exit code: $exitCode")

            if (exitCode != 0) {
                Log.e("SU_COMMAND", "Command failed with exit code $exitCode.")
                result.success(false)
            } else {
                Log.i("SU_COMMAND", "Command executed successfully.")
                result.success(true)
            }
        } catch (e: Exception) {
            Log.e("SU_COMMAND", "Exception occurred: ${e.message}")
            handleCommandException(e, result)
        }
    }

    private fun handleCommandException(e: Exception, result: Result) {
        result.error("Exception", "An exception occurred: $e", null)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}